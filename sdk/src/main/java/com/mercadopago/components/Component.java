package com.mercadopago.components;

import android.support.annotation.NonNull;


/**
 * Created by vaserber on 10/20/17.
 */

public abstract class Component<T> {

    private T props;
    private final ActionDispatcher dispatcher;

    public Component(@NonNull final ActionDispatcher dispatcher) {
        this.props = null;
        this.dispatcher = dispatcher;
    }

    public Component(@NonNull final T props, @NonNull final ActionDispatcher dispatcher) {
        this.props = props;
        this.dispatcher = dispatcher;
        //TODO: move lifecycle methods calls to component manager
        setProps(props);
        applyProps(props);
    }

    public ActionDispatcher getDispatcher() {
        return dispatcher;
    }

    public void setProps(T props) {
        this.props = props;
    }

    public T getProps() {
        return props;
    }

    public abstract void applyProps(@NonNull final T props);

}
