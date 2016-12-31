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
    location.hash = "#jogo";
});

$(document).on('click', '.placar', function () {
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
    $("#div-fixo").hide();
    $("#div-aguardando-jogo").hide();
    $("#div-pergunta").hide();
    $("#div-placar").hide();
    location.hash = "";
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