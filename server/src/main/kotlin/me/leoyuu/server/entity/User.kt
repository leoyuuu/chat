package me.leoyuu.server.entity

data class User(val uid:Int = 0, var sid:String, var name:String) {
    override fun hashCode() = uid

    override fun equals(other: Any?): Boolean {
        return other != null && other.javaClass == javaClass && other.hashCode() == hashCode()
    }
}
