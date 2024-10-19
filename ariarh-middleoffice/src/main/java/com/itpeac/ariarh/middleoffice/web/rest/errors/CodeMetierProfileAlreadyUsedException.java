package com.itpeac.ariarh.middleoffice.web.rest.errors;

public class CodeMetierProfileAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public CodeMetierProfileAlreadyUsedException() {
        super(ErrorConstants.CODE_METIER_ALREADY_USED_TYPE, "Code Metier already used!", "profileManagement", "profileexists");
    }
}
