package purpleagle.com.model.functions

fun Int?.toIntOr(x: Int): Int{
    return try{
        this!!.toInt()
    }catch (e: Exception){
        x
    }
}

fun String?.toIntOr(x: Int): Int{
    return try{
        this!!.toInt()
    }catch (e: Exception){
        x
    }
}