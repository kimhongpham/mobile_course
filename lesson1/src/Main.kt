fun main() {
    vd9()
}

fun vd1() {
    val namSinh = 2003
    val namHienTai = 2025
    val tuoi = namHienTai - namSinh

    val can = arrayOf("Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ", "Canh", "Tân", "Nhâm", "Quý")
    val chi = arrayOf("Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi", "Thân", "Dậu", "Tuất", "Hợi")

    val canIndex = (namSinh - 4) % 10
    val chiIndex = (namSinh - 4) % 12
    val amLich = "${can[canIndex]} ${chi[chiIndex]}"

    println("Tuổi của bạn là: $tuoi")
    println("Năm sinh âm lịch: $amLich")
}

fun vd2() {
    println("Nhập số:")

    var results: Int = 5

    when (results) {
        0 -> println("No results")
        in 1..39 -> println("Got results!")
        else -> println("That's a lot of results!")
    }
}

fun vd3() {
    val pets = arrayOf("dog", "cat", "canary")
    for (element in pets) {
        print(element + " ")
    }
    println()
    for ((index, element) in pets.withIndex()) {
        println("Item at $index is $element\n")
    }
}

fun vd11(){
    var Tong = 0
    for (i in 1..10) Tong = Tong + i
    println(Tong)
}

fun vd12() {
    print("Nhập n: ")
    var n: Int = readLine()?.toIntOrNull() ?: 0

    var i: Int = 0
    var j: Int = 0

    for (i in 1..n) {
        for (j in 1..i) {
            print("* ")
        }
        println()
    }
}

fun vd13() {
    print("Nhập số a: ")
    var a = readLine()?.toIntOrNull() ?: 0

    print("Nhập số b: ")
    var b = readLine()?.toIntOrNull() ?: 0

    // Thuật toán trừ dần
    while (a != b) {
        if (a > b)
            a = a - b
        else
            b = b - a
    }

    println("UCLN của hai số là: $a")
}

fun vd7(){
    repeat(5){
        println("Hi")
    }
}
fun vd8(){

    val instruments = listOf("trumpet", "piano", "violin")
    println(instruments)

    val myList = mutableListOf("trumpet", "piano", "violin")
    println(myList)
    myList.remove("violin")
    println(myList)
}

fun vd9(){
    val pets = arrayOf("dog", "cat", "canary")
    println(java.util.Arrays.toString(pets))
    println(pets)

    val mix = arrayOf("hats", 2)
    println(java.util.Arrays.toString(mix))

    val numbers = intArrayOf(1, 2, 3)
    println(java.util.Arrays.toString(numbers))
}

fun vd10(){
    val numbers = intArrayOf(1,2,3)
    val numbers2 = intArrayOf(4,5,6)
    val combined = numbers2 + numbers
    println(java.util.Arrays.toString(combined))
}

fun vd4() {
    print("Nhập số a: ")
    val a = readLine()?.toIntOrNull() ?: 0

    print("Nhập số b: ")
    val b = readLine()?.toIntOrNull() ?: 0

    print("Nhập số c: ")
    val c = readLine()?.toIntOrNull() ?: 0

    val min = minOf(a, b, c)
    val max = maxOf(a, b, c)

    println("Min trong ba số là: $min")
    println("Max trong ba số là: $max")
}

fun vd5() {
    print("Nhập chiều cao m: ")
    val m = readLine()?.toIntOrNull() ?: 0

    print("Nhập chiều rộng n: ")
    val n = readLine()?.toIntOrNull() ?: 0

    if (m <= 0 || n <= 0) {
        println("Chiều cao và chiều rộng phải lớn hơn 0.")
        return
    }

    for (i in 1..m) {
        for (j in 1..n) {
            if (i == 1 || i == m || j == 1 || j == n) {
                print("* ")
            } else {
                print("  ")
            }
        }
        println()
    }
}

fun vd6() {
    print("Nhập số a để kiểm tra: ")
    val a = readLine()?.toIntOrNull() ?: 0

    if (a <= 1) {
        println("$a không phải là số nguyên tố.")
        return
    }

    var isPrime = true // Giả sử ban đầu là số nguyên tố

    for (i in 2..Math.sqrt(a.toDouble()).toInt()) {
        if (a % i == 0) {
            isPrime = false
            break
        }
    }

    if (isPrime) {
        println("$a là số nguyên tố.")
    } else {
        println("$a không phải là số nguyên tố.")
    }
}





