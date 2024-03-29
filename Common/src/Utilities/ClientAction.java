package Utilities;

/**
 * Werte dieses Enums werden für die Kommunikation zwischen Client und Server verwendet.
 * Der Client schickt dem Server mittels dieser Werte, welche Aktion ausgeführt werden soll.
 */
public enum ClientAction {
    LOGIN,
    LOGOUT,

    ADD_EMPLOYEE,
    ADD_CUSTOMER,
    REMOVE_PERSON,
    GET_CUSTOMER_LIST,
    GET_EMPLOYEE_LIST,

    ADD_ARTICLE,
    REMOVE_ARTICLE,
    UPDATE_STOCK,
    GET_ARTICLE_LIST,
    GET_EVENT_LIST,

    ADD_ARTICLE_TO_SHOPPINGCART,
    REMOVE_ARTICLE_FROM_SHOPPINGCART,
    BUY_SHOPPINGCART,
    CLEAR_SHOPPINGCART,
    GET_SHOPPINGCART
}
