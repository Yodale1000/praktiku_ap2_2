fun main() {
    val task1 = SingleTask(
        "Gerichte planen",
        "Die konkreten Mahlzeiten planen",
        1,
        listOf("Rezepte aussuchen", "Verfügbarkeit an Tagen checken"),
        15,
        Status.DONE,
    )

    val task2 = RecurringTask(
        "Einkaufsliste schreiben",
        "Die benötigten Lebensmittel aufschreiben",
        2,
        listOf("Zutaten aus Rezepten aufschreiben", "Vorräte checken", "Liste anpassen"),
        30,
        Status.DOING,
    )

    val task3 = SingleTask(
        "Einkaufen",
        "Alle benötigten Lebensmittel kaufen",
        3,
        listOf("Pfand einpacken", "Zum Supermarkt fahren", "Einkaufen", "Nach Hause fahren", "Einkäufe einräumen"),
        90,
        Status.TODO,
    )

    val project = Project(
        "Wocheneinkauf",
        "Essen für die kommende Woche besorgen",
        3,
        mutableListOf(task1, task2, task3),
        Status.DOING
    )
    println(task1.getSummary())
    println(task2.getSummary())
    println(task3.getSummary())
    println(project.getSummary())


    val a: Prioritizable = Unit(" Grundlegende Arbeitseinheit ", " Das ist die Beschreibung ", 10, Status.TODO) // OK
    val b = " Aufgabe " // OK
    val c = RecurringTask(
        " Deadline Task ", " Mit Deadline ", 3,
        listOf(" Schritt 1 ", " Schritt 2 "), 120, Status.TODO, -3 // OK
    ) // OK
    // val d: Unit = Prioritizable() // CF
    val e: Unit = c // OK
    val f: Unit = SingleTask(" Spezifischer Task ", " Beschreibung ", 5, null, 45, Status.DOING) // OK
    // val g: Prioritizable = f as RecurringTask // LF
    val h: Prioritizable = c // OK
}