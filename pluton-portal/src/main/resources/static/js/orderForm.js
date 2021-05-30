
let addressApp = new Vue({
    el: "#addressApp",
    data: {
        addressSp: {},
        address:''
    },
    methods: {
        loadAddress: function () {
            $.ajax({
                url: "/address",
                method: "get",
                success: function (r) {
                    if (r.code=200){
                        addressApp.addressSp = r.data
                        console.log(addressApp.addressSp)
                    }else{
                        alert(r.message)
                    }

                }
            })
        },
        deleteAddress:function (id) {

        },
        select: function (data) {
            this.address=data
        }
    },
    created() {
        this.loadAddress()
    }
})

var Mains = {
    data() {
        return {
            json: {},
            price: 0
        }
    },
    created() {
        this.loadShopping()
    },
    methods: {
        loadShopping: function () {
            var _this = this
            $.ajax({
                url: "/trolley/orderForm/session",
                method: "get",
                success: function (r) {
                    console.log(r)
                    if (r.code==200){
                        _this.json = r.data
                        _this.sumPrice(r.data)
                    }else{
                        alert(r.message)
                    }

                }
            })
        },
        orderForm:function () {
            let data={
                amount:this.price,
                shopping:JSON.stringify(this.json)
            }
            $.ajax({
                url:"/trolley/pay",
                method:"get",
                data:data,
                success:function (r) {
                    console.log(r)
                    window.location.href=r
                }
            })
            console.log(this.json)
        },
        sumPrice: function (data) {
            for (let i = 0; i < data.length; i++) {
                this.price += data[i].spPrice*1
            }
        }
    },
}
var Ctor = Vue.extend(Mains)
new Ctor().$mount('#orderForm')


/*/注意：导航 依赖 element 模块，否则无法进行功能性操作*/
layui.use(['element', 'layer'], function () {
    var element = layui.element;
    var layer = layui.layer;

    $("#add").click(function () {
        layer.open({
            type: 2,
            shadeClose: true,
            title:"地址信息",
            shade: false,
            area: '600px',
            offset: 'auto',
            content: 'address.html' //iframe的url
        });
    })
    //…
});
$('input:radio:first').attr('checked', 'checked');