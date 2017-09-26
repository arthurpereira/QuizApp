/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onbeforeunload = function () {
    return "Você deseja encerrar a aplicação?";
};

// Lógica botões de navegação
$(document).on('click', '.jogo', function () {
    if (location.hash.split("#")[1] === "jogo") {
        $("html, body").animate({scrollTop: 0}, 500);
    } else if (location.hash.split("#")[1] === "placar") {
        $("html, body").scrollTop(0);
    }
    location.hash = "#jogo";
});

$(document).on('click', '.placar', function () {
    if (location.hash.split("#")[1] === "jogo") {
        $("html, body").scrollTop(0);
        visualizouPlacar();
    } else if (location.hash.split("#")[1] === "placar") {
        $("html, body").animate({scrollTop: 0}, 500);
    }
    location.hash = "#placar";
});

function iniciarPartidaJS() {
    $("#div-placar").removeClass("display-none");
    iniciarPartida();
    $("#div-fim-do-jogo").addClass("display-none");
    $("#div-fim-do-jogo").hide();
    $("#div-fixo").show();
    $("#div-aguardando-jogo").hide();
    $("#div-pergunta").show();
}

function habilitarAlternativasJS() {
    habilitarAlternativas();
}

function mudarPerguntaJS() {
    mudarPergunta();
    $("html, body").animate({scrollTop: 0}, 500);
}

function mostrarRespostaCertaJS() {
    mostrarRespostaCerta();
}

function jogo() {
    $("#div-placar").hide();
    $("#div-aguardando-jogo").show();
    $("#div-pergunta").show();
    $("#span-jogo").removeClass("nav-inativo");
    $("#span-jogo").addClass("nav-ativo");
    $("#span-placar").removeClass("nav-ativo");
    $("#span-placar").addClass("nav-inativo");
    location.hash = "#jogo";
}

function placar() {
    $("#div-aguardando-jogo").hide();
    $("#div-pergunta").hide();
    $("#div-placar").removeClass("display-none");
    $("#div-placar").show();
    $("#span-jogo").removeClass("nav-ativo");
    $("#span-jogo").addClass("nav-inativo");
    $("#span-placar").removeClass("nav-inativo");
    $("#span-placar").addClass("nav-ativo");
}

function fimdojogo() {
    $("#div-topo").hide();
    $("#div-rodape").hide();
    $("#div-aguardando-jogo").hide();
    $("#div-pergunta").hide();
    $("#div-placar").hide();
    $(".logo-topo").addClass("fim-do-jogo");
    $(".logo-lades").addClass("fim-do-jogo");
    location.hash = "";
}

function responderQuestionario() {
    $("#div-fim-do-jogo").hide();
    $("#div-questionario").show();
}

function agradecimento() {
    $("#div-questionario").hide();
    $("#div-fundo").hide();
    $("#div-agradecimento").removeClass("display-none");
}

window.onload = function () {
    window.addEventListener('hashchange', function () {
        if (location.hash.split("#")[1] === "jogo") {
            jogo();
        } else if (location.hash.split("#")[1] === "placar") {
            placar();
        }
    });
};