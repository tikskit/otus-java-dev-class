package ru.tikskit.ms;

import ru.otus.core.model.User;
import ru.otus.messagesystem.client.MessageCallback;

public interface FrontendService {

    /**
     *
     * @param userData пользователь, которого нужно сохранить
     * @param dataConsumer Колбэк для ответного сообщения. В случае с вэбсокетами это была бы отправка данных
     *                     пользователя на страницу, которая к нам подключилась
     */
    void saveUser(UserData userData, MessageCallback<UserData> dataConsumer);
}

