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
        $("#conteudo").animate({scrollTop: 0}, 500);
    } else if (location.hash.split("#")[1] === "placar") {
        $("#conteudo").scrollTop(0);
    }
    location.hash = "#jogo";
});

$(document).on('click', '.placar', function () {
    if (location.hash.split("#")[1] === "jogo") {
        $("#conteudo").scrollTop(0);
    } else if (location.hash.split("#")[1] === "placar") {
        $("#conteudo").animate({scrollTop: 0}, 500);
    }
    location.hash = "#placar";
});

function iniciarPartidaJS() {
    $("#div-placar").removeClass("display-none");
    iniciarPartida();
    $("#div-fim-do-jogo").addClass("display-none");
    $("#div-fim-do-jogo").hide();
    $("#panel-partida-nao-iniciada").addClass("display-none");
    $("#panel-partida-nao-iniciada").hide();
    $("#div-pergunta").show();
}

function habilitarAlternativasJS() {
    habilitarAlternativas();
}

function mudarPerguntaJS() {
    mudarPergunta();
    $("#conteudo").animate({scrollTop: 0}, 500);
}

function mostrarRespostaCertaJS() {
    mostrarRespostaCerta();
}

function jogo() {
    $("#div-placar").addClass("display-none");
    $("#sidebar").removeClass("sidebar-open");
    $("#conteudo").removeClass("sidebar-open");
    $("#div-aguardando-jogo").removeClass("display-none");
    $("#div-pergunta").removeClass("display-none");
    $("#span-jogo").removeClass("nav-inativo");
    $("#span-jogo").addClass("nav-ativo");
    $("#span-placar").removeClass("nav-ativo");
    $("#span-placar").addClass("nav-inativo");
    location.hash = "#jogo";
}

function placar() {
    $("#div-aguardando-jogo").addClass("display-none");
    $("#div-pergunta").addClass("display-none");
    $("#conteudo").addClass("sidebar-open");
    $("#sidebar").addClass("sidebar-open");
    $("#div-placar").removeClass("display-none");
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
    $("#separator").hide();
    $("#conteudo").addClass("fim-do-jogo");
    $("#sidebar").addClass("fim-do-jogo");
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
    $("#div-agradecimento").show();
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