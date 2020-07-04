package Utilities;

/**
 * Dieses Enum dient der Unterscheidung von Employee und Customer.
 * Anwendungsbeispiel:
 * z.B. kann der Server dem Client "sagen" das die angemeldete Person ein PersonType.Employee ist
 * Daraufhin kann der Client die entsprechende Ansicht anzeigen
 */
public enum PersonType {
    Employee,
    Customer,
    Guest
}
