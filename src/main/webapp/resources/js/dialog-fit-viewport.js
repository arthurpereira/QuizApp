/**
 * Created by arthurpereira on 31/03/17.
 */
function handleResizeDialog(dialog) {
    var el = $(dialog.jqId);
    var win = $(window);

    if (el.height() > win.height()) {
        var winHeight = $( window ).height();
        var contentPadding = dialog.content.innerHeight() - dialog.content.height();

        dialog.content.css("max-height", (winHeight - dialog.titlebar.outerHeight() - contentPadding - dialog.footer.outerHeight()) + "px" );
        dialog.content.css("overflow", "auto");
    }

}