package ru.tikskit.ms;

import ru.otus.core.model.User;
import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;


public class FrontendServiceImpl implements FrontendService {

    private final MsClient msClient;
    private final String databaseServiceClientName;

    public FrontendServiceImpl(MsClient msClient, String databaseServiceClientName) {
        this.msClient = msClient;
        this.databaseServiceClientName = databaseServiceClientName;
    }


    @Override
    public void saveUser(UserData userData, MessageCallback<UserData> dataConsumer) {
        /*
         С помощью клиента создаем сообщение, которое должно быть отправлено сервису БД.
             userData - само сообщение
             MessageType.USER_DATA - тип сообщения
             dataConsumer - коллбэк, который клиент зарегистрирует на ответ
         */
        Message outMsg = msClient.produceMessage(databaseServiceClientName, userData,
                MessageType.USER_DATA, dataConsumer);
        // Кладем полученное сообщение в систему
        msClient.sendMessage(outMsg);

    }
}
