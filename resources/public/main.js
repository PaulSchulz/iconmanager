// Constants.
var SAMPLE_WIDTH = 380,
    SAMPLE_HEIGHT = 50,
    DEFAULT_SAMPLE_TEXT = "Sample text";

var fonts, // The full list of fonts.
    sampleText = DEFAULT_SAMPLE_TEXT,
    iconsDir = "resources/public/icons"; // The current icons directory.

// Main entry point (executes when the page has fully loaded).
$(function() {

    // Activate navigational links.
    $("#iconsinfolder").click(function(event) {
        event.preventDefault();
        browseDir(iconsDir);
    });

    // Get current font directory.
    $.get("icondir").success(function(dir) {
        if(dir)
            iconsInFolder(dir);
        else
            $.get("homedir").success(function(dir) {
                browseDir(dir);
            }).error(function() {
                status("Error determining home directory.",true);
            });
    }).error(function() {
        status("Error determining icon directory (".concat(icondir,")"),true);
    });

}); // End of main entry point.

// Show a new status text.
function status(text,errorFlag) {
  $("#status").toggleClass("error",!!errorFlag).text(!text ? "" : text);
}

// Let the user pick an icon directory.
function browseDir(dir) {
    iconsDir = dir;
    status();
    $("#icons").empty();
    $.getJSON("subdirs/" + encodeURIComponent(dir)).success(function(subdirs) {
    // Scroll to top.
    $("html,body").animate({scrollTop : 0},"slow");

    // We know the subdirectories. Create the DOM for it.
    var ul = $("<ul/>"),
        li = $("<li/>").appendTo(ul),
        scan = $("<button></button>").text("Scan " + dir).appendTo(li);

    // Finish browsing.
    scan.on("click",function() {
        iconsInFolder(dir);
    });

    // Add subdirectories.
    subdirs.forEach(function(subdir,index) {
      var li = $("<li/>"),
          a = $("<a/>");

      // Browse to subdirectory.
      a.attr("href","#")
        .text(subdir.name)
        .on("click",function(event) {
          event.preventDefault();
          browseDir(subdir.path);
        }).appendTo(li);

      li.appendTo(ul);
    });

    $("#browser").empty().append(ul).show();
  }).error(function() {
    status("Unable to determine subdirectories.",true);
  });
}

// Display fonts in a folder.
function iconsInFolder(dir) {
    iconsDir = dir;
    $("#browser").hide();
    status("Scanning for icons in " + dir + "...");
  $.getJSON("icons/" + encodeURIComponent(dir)).success(function(data) {
    if(!data.length) {
        status("No icons found in " + dir);
        //TODO: Offer a link to the browser.
      return;
    }
    icons = data;
      status("Showing icons in " + dir);

      // We show all icons but don't display the previews yet.
      var block = $("<ul/>").attr("class","iconslist");
      icons.forEach(function(icon) {
          var li = $("<li/>").appendTo(block),
              header = $("<header/>").text(icon.filename).appendTo(li),
              dummy = $('<div class="dummy"/>').appendTo(li);
          dummy.width(SAMPLE_WIDTH).height(SAMPLE_HEIGHT).text("Generating preview...");
          icon.hasName = false; // We don't have a font name yet.
          icon.sampleUpToDate = false; // The sample is not shown yet.
          li.data("icon",icon);
      });
      $("#icons").empty().append(block);

      // Refresh samples upon new sample text.
      $("#sampletext").unbind("submit").submit(function(event) {
          event.preventDefault();
          var newText = $.trim($("#sampletext input:first").val());
          if(newText)
              sampleText = newText;
          else
              sampleText = DEFAULT_SAMPLE_TEXT;
          fonts.forEach(function(font) {
              font.sampleUpToDate = false;
          });
          refreshSamples();
      });

      // Refresh samples when scrolling.
      $(window).unbind("scroll").scroll(refreshSamples);

      // Refresh now for the first time.
      refreshSamples();
  }).error(function() {
      status("Could not load icon list.",true);
  });
}

// Update all fonts that are visible (plus a few before and after).
function refreshSamples() {
  var scrollTop = $(window).scrollTop(),
      viewportHeight = $(window).height(),
      extent = 1;

    $(".iconlist li").filter(function() {
        var y = $(this).offset().top - scrollTop;
        return y >= -extent * viewportHeight && y <= (1 + extent) * viewportHeight;
  }).each(function() {
      var icon = $(this).data("icon"),
          that = this;

    if(!icon.hasName) {
        // Let's find out this icon's name.
        $.get(["iconname",font.id].join("/")).success(function(name) {
            var header = $("header",that);
            header.text(name).off().on("mouseover",function(event) {
                header.text(font.filename);
            }).on("mouseout",function(event) {
                header.text(name);
            });
            icon.hasName = true;
        });
    }

      if(!icon.sampleUpToDate) {
          // Let's show a proper icon sample.
          var img = $("<img/>");
          img.one("load",function() {
              $(".dummy, img",that).replaceWith(img);
              icon.sampleUpToDate = true;
          }).attr("src",["sample",icon.id,SAMPLE_WIDTH,SAMPLE_HEIGHT,encodeURIComponent(sampleText)].join("/"));
          if(img.get(0).complete)
              img.load(); // Fire load event if cached.
    }
  });
}
