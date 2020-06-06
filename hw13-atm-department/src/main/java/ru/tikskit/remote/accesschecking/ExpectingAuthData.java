package ru.tikskit.remote.accesschecking;


/**
 * Этот класс введен просто для того, чтобы все ожидаемые данные авторизации и аутентификации явным образом задавались
 * в нем, чтобы не нужно было их искать по коду, если для отладки или в каких-либо иных целях их понадобилось бы
 * поменять
 */
interface ExpectingAuthData {
    String USER = "root";
    String PASS = "1234";
    String[] REMOTE_USER_LIST = new String[]{"root", "tester"};
}
