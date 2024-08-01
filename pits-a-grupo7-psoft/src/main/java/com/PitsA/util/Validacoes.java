package com.PitsA.util;

import com.PitsA.exception.accessCode.AccessCodeIncorrectException;
import com.PitsA.exception.accessCode.MustHaveAnAccessCodeException;
import com.PitsA.exception.accessCode.TheAccessCodeMustHaveSixDigitsException;

public class Validacoes {

    public static void validaCodigo(Integer codigoAcesso) throws MustHaveAnAccessCodeException, TheAccessCodeMustHaveSixDigitsException {
        if (codigoAcesso == null) throw new MustHaveAnAccessCodeException();
        if (codigoAcesso.toString().length() != 6) throw new TheAccessCodeMustHaveSixDigitsException();
    }

    public static void autentica(Integer codigoAcesso, Integer codigoAcessoRecebido) throws AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, MustHaveAnAccessCodeException {
        validaCodigo(codigoAcessoRecebido);
        if (!codigoAcesso.equals(codigoAcessoRecebido)) throw new AccessCodeIncorrectException();
    }
}
