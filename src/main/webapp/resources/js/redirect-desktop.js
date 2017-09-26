/**
 * Created by arthurpereira on 27/06/17.
 */

var mobile = (/iphone|ipad|ipod|android|blackberry|mini|windows\sce|palm/i.test(navigator.userAgent.toLowerCase()));
if (!mobile || screen.width >= 768) {
    window.location.replace("/quiz-app/jogo/desktop/");
}