import org.jetbrains.exposed.sql.Table

class DogsTable {
    object DogsTable : Table("dogs") {
        val id = integer("id").autoIncrement()
        val name = varchar("name", 100)
        val age = integer("age").nullable()
        val breed = varchar("breed", 100).default("Mestizo")
        val history = text("history").nullable()
        val sterilized = text("sterilized").nullable()
        val adopted = text("adopted").nullable()

        override val primaryKey = PrimaryKey(id)



    }
}