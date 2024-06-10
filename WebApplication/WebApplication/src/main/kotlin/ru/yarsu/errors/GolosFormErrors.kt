package ru.yarsu.errors

class GolosFormErrors(val name: String?) {

    fun nullToEmpty(value: String?): String{
        if(value == null){
            return ""
        }
        return value
    }

    fun startsWithCapital(name: String): Boolean {
        return name[0].isUpperCase()
    }

    fun containsOnlyLatinLettersAndDigits(name: String): Boolean {
        val regex = Regex("[a-zA-Z0-9]+")

        return regex.matches(name)
    }

    fun lenghtNick(name: String): Boolean {
        if (name.length !in 3..7) {
            return false
        }
        return true
    }

    fun getListErrors(value: String?): List<String>{
        val listErrors = mutableListOf<String>()
        if(value == null || value == ""){
            listErrors.add("Поле должно быть заполнено")
        }
        else{
            if(!startsWithCapital(value)){
                listErrors.add("Никнейм должен начинаться с большой буквы")
            }
            if(!containsOnlyLatinLettersAndDigits(value)){
                listErrors.add("Никнейм должен содержать только латинские буквы(возможно вместе с цифрами)")
            }
            if(!lenghtNick(value)){
                listErrors.add("Длина никнейма должна быть не менее 3-х и не более 7-ми символов")
            }
        }
        return listErrors
    }

    fun getFormErrors(): GetErrors {
        val formErrors = mutableMapOf<String, List<String>>()
        formErrors[nullToEmpty(name)] = getListErrors(name)
        return GetErrors(formErrors)
    }
}