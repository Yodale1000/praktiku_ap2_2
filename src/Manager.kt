class Manager(val projects: MutableList<Project>) {
    val todo = mutableListOf<Task>()

    fun generateToDoList() {
        for (project in projects) {
            for (task in project.tasks) {
                if (task.status != Status.DONE) {
                    todo.add(task)
                }
            }
        }
    }

    fun getPriorityToDo(): MutableList<Task> {
        var newToDo = mutableListOf<Task>()
        for (task in todo) {
            if (task.priority == Priority.HIGH && task.status != Status.DONE) {
                newToDo.add(task)
            }
        }
        return newToDo
    }

    fun getAvgTime(): Int {
        var totalTime: Int = 0
        for (task in todo) {
            if (task.priority == Priority.HIGH) {
                totalTime += task.estimatedTime
            }
        }
        return totalTime / todo.size
    }
}