package com.nick.model.type

// bag资源：通俗的说，就是这类资源在赋值的时候，不能随便赋值，只能从事先定义好的值中选取一个赋值。
// 类型为values的资源除了是string之外，还有其它很多类型的资源，其中有一些比较特殊，如bag、style、plurals和array类的资源。
// 这些资源会给自己定义一些专用的值，这些带有专用值的资源就统称为Bag资源。
class ResTable_map(var name: ResTable_ref, // bag资源项ID,
                   var value: Res_value) { //bag资源项值
    override fun toString(): String {
        return "mapEntry: " + name.toString() + ",value = " + value.toString()
    }

}
