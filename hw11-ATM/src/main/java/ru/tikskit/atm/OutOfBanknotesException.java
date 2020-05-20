package ru.tikskit.atm;

/**
 * В хранилище банкнот отсутствуют или недостаточное количество банкнот номинала нужного для выдачи
 */
class OutOfBanknotesException extends Exception {

    public OutOfBanknotesException(int banknotesRequired) {
        super(String.format("Недостаточно банкнот: %d!", banknotesRequired));
    }
}
