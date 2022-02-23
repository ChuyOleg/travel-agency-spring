"use strict";

const QUESTION_MARK = "?";
const LANG_PARAMETER = "lang";

const EN_Button = document.getElementById("englishLanguageButton");
const UA_Button = document.getElementById("ukrainianLanguageButton");

const callback = language => () => {
    const URI = window.location.pathname;
    const SEARCH_PARAMS = new URLSearchParams(window.location.search);

    SEARCH_PARAMS.set(LANG_PARAMETER, language);

    const NEW_PARAMETERS = SEARCH_PARAMS.toString();

    window.location.href = URI + QUESTION_MARK + NEW_PARAMETERS;
}

EN_Button.addEventListener("click", callback("en"));

UA_Button.addEventListener("click", callback("ua"));