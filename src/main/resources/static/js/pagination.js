"use strict";

const QUESTION_MARK_SYMBOL = "?";
const PAGE_URL_ARGUMENT_NAME = "page";

const pageNumber = document.getElementById("pageNumber");

const prevPageButton = document.getElementById("prevPageButton");
const nextPageButton = document.getElementById("nextPageButton");

const paginationCallback = addition => () => {
    const URI = window.location.pathname;
    const ACTIVE_PAGE_NUMBER = parseInt(pageNumber.innerText);
    const SEARCH_PARAMS = new URLSearchParams(window.location.search);

    SEARCH_PARAMS.set(PAGE_URL_ARGUMENT_NAME, (ACTIVE_PAGE_NUMBER + addition).toString());

    const NEW_PARAMETERS = SEARCH_PARAMS.toString();

    window.location.href = URI + QUESTION_MARK_SYMBOL + NEW_PARAMETERS;
}

prevPageButton.addEventListener("click", paginationCallback(-1));

nextPageButton.addEventListener("click", paginationCallback(1));
