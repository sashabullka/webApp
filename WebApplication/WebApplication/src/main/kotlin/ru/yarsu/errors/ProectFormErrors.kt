package ru.yarsu.errors

class ProectFormErrors(val namePunkt: String?, private val nameTerritory: String?, val coordinates: String?, private val descripton: String?) {


    fun startsWithCapital(name: String): Boolean {
        return name[0].isUpperCase()
    }

    fun isValidCoordinateFormat(name: String): Boolean {
        val regex = Regex("""^\d{1,3}°\d{1,2}'\d{1,2}\.\d+[""](N|S|E|W) \d{1,3}°\d{1,2}'\d{1,2}\.\d+[""](N|S|E|W)$""")

        return regex.matches(name)
    }

    fun containsOnlyLatinLettersAndDigits(name: String): Boolean {
        val regex = Regex("[a-zA-Z0-9]+( [a-zA-Z0-9]+)*")

        return regex.matches(name)
    }

    fun getListErrors(value: String?): List<String>{
        val listErrors = mutableListOf<String>()
        if(value == null || value == ""){
            listErrors.add("Поле должно быть заполнено")
        }
        else{
            if(!startsWithCapital(value)){
                listErrors.add("Поле должно начинаться с большой буквы")
            }
            if(!containsOnlyLatinLettersAndDigits(value)){
                listErrors.add("Поле должно содержать только латинские буквы(возможно вместе с цифрами)")
            }
        }
        return listErrors
    }

    fun getListErrorsCoordinates(value: String?): List<String>{
        val listErrors = mutableListOf<String>()
        if(value == null || value == ""){
            listErrors.add("Поле должно быть заполнено")
        }
        else{
            if(!isValidCoordinateFormat(value)){
                listErrors.add("Пожалуйста введите координаты в валидном формате. Пример: 27°8'21.45\"N 23°14'34.21\"E")
            }

        }
        return listErrors
    }

    fun nullToEmpty(value: String?): String{
        if(value == null){
            return ""
        }
        return value
    }

    fun getFormErrors(): GetErrors {
        val formErrors = mutableMapOf<String, List<String>>()
        formErrors[nullToEmpty(namePunkt)] = getListErrors(namePunkt)
        formErrors[nullToEmpty(nameTerritory)] = getListErrors(nameTerritory)
        formErrors[nullToEmpty(descripton)] = getListErrors(descripton)
        formErrors[nullToEmpty(coordinates)] = getListErrorsCoordinates(coordinates)

        return GetErrors(formErrors)
    }
}