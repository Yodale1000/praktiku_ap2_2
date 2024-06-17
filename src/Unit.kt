class DeadlineException(message: String) : Exception(message)

open class Unit(
    val title: String,
    private val description: String,
    var deadline: Int,
    var status: Status
) : Prioritizable {
    protected fun changeStatus(status: Status) {
        this.status = status
    }

    open fun getSummary(): String {
        return "'${title}' mit der Beschreibung '${description}' muss bis in $deadline Tag(en) erledigt sein. " +
                "Der aktuelle Status ist ${status}."
    }

    private fun deadlinePrio(): Double {
        return when {
            deadline <= 7 -> 1.0
            deadline in 8..31 -> 2.0
            else -> 3.0
        }
    }

    private fun statusPrio(): Double {
        return when (status) {
            Status.DOING -> 1.0
            Status.TODO -> 2.0
            Status.DONE -> 3.0
        }
    }

    override fun prioritize(): Double {
        return (statusPrio() + deadlinePrio()) / 2
    }

    val priority: Priority
        get() = Priority.fromFactor(this.prioritize())
}

abstract class Task(
    title: String,
    description: String,
    deadline: Int,
    private var steps: List<String>? = null,
    var estimatedTime: Int = 0,
    status: Status
) : Unit(title, description, deadline, status) {


    override fun getSummary(): String {
        return "Die Aufgabe " + super.getSummary() + "Die Arbeitsschritte sind: ${steps?.joinToString() ?: "Keine"}. " +
                "Es wird voraussichtlich $estimatedTime Minuten dauern."
    }

    private fun stepsPrio(): Double {
        return when {
            (steps?.size ?: 0) > 11 -> 1.0
            (steps?.size ?: 0) in 5..10 -> 2.0
            else -> 3.0
        }
    }

    private fun estimatedTimePrio(): Double {
        return when {
            estimatedTime < 60 -> 1.0
            estimatedTime in 60..180 -> 2.0
            else -> 3.0
        }
    }

    override fun prioritize(): Double {
        return (stepsPrio() + estimatedTimePrio()) / 2
    }
}

class SingleTask(
    title: String,
    description: String,
    deadline: Int,
    steps: List<String>?,
    estimatedTime: Int,
    status: Status
) : Task(title, description, deadline, steps, estimatedTime, status) {
    val reminder: Int
        get() {
            return if (deadline > 1) deadline - 2 else 0
        }
}

class RecurringTask(
    title: String,
    description: String,
    deadline: Int,
    steps: List<String>?,
    estimatedTime: Int,
    status: Status,
    var frequency: Int = 0
) : Task(title, description, deadline, steps, estimatedTime, status)


class Project(
    name: String,
    description: String,
    deadline: Int,
    var tasks: MutableList<Task>,
    status: Status
) : Unit(name, description, deadline, status) {
    private val progress: Double
        get() {
            if (tasks.isNotEmpty()) {
                var count = 0.0
                for (task in tasks) {
                    if (task.status == Status.DONE) count++
                }
                return (count / tasks.size) * 100
            } else {
                return 0.0
            }
        }

    override fun getSummary(): String {
        return "Das Projekt " + super.getSummary() + "Das Projekt beinhaltet ${tasks.size} Aufgaben. " +
                "Aktuell sind ${String.format("%.2f", progress)}% abgeschlossen."
    }

    private fun addTask(task: Task) {
        if (task.deadline < this.deadline)
            tasks.add(task)
        else
            throw DeadlineException("Deadline der Aufgabe nach Deadline des Projekts.")
    }

    fun checkTasks() {
        for (task in tasks) {
            when (task) {
                is SingleTask -> {
                    if (task.reminder == 0) {
                        println("Alarm für Task: ${task.title}")
                    }
                }

                is RecurringTask -> {
                    if (task.deadline < 0 && task.deadline < this.deadline) {
                        task.deadline += task.frequency
                        println("Neue deadline für Task: ${task.title} = ${task.deadline}")
                    } else {
                        throw DeadlineException("Deadline der Aufgabe nach Deadline des Projekts.")
                    }
                }
            }
        }
    }


    override fun prioritize(): Double {
        return tasks.sumOf { it.prioritize() } / 2
    }
}

enum class Status {
    TODO,
    DOING,
    DONE
}