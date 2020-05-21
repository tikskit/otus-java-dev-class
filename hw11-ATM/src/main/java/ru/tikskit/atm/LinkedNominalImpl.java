package ru.tikskit.atm;

class LinkedNominalImpl extends NominalImpl implements LinkedNominal {

    private final LinkedNominal smaller;

    LinkedNominalImpl(int value, LinkedNominal smaller) {
        super(value);
        this.smaller = smaller;
    }


    @Override
    public LinkedNominal getSmaller() {
        return smaller;
    }
}
