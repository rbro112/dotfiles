package models

@Serializable
data class NotionTodo(
    val key: String,
    val value: String,
    val checked: Boolean = false,
    // TODO: Priority
    // TODO: Due date
)