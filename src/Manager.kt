class Manager(private val projects: MutableList<Project>) {
    private val todo = mutableListOf<Task>()

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
        val newToDo = mutableListOf<Task>()
        for (task in todo) {
            if (task.priority == Priority.HIGH && task.status != Status.DONE) {
                newToDo.add(task)
            }
        }
        return newToDo
    }

    fun getAvgTime(): Int {
        var totalTime = 0
        for (task in todo) {
            if (task.priority == Priority.HIGH) {
                totalTime += task.estimatedTime
            }
        }
        return totalTime / todo.size
    }
}