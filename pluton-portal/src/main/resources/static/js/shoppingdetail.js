var Mains = {
    data() {
        return {
            showBt:false,
            json: '',
            multipleSelection: [],
            settleBtn:true
        }
    },
    created() {
        this.showdetail()
    },
    methods: {
        settleaccounts:function () {
            let shopping=this.multipleSelection
            $.ajax({
                url:"/trolley/orderForm",
                method:"get",
                data:{shopping:JSON.stringify(shopping)},
                success:function (r){
                    if (r.code==200){
                        location.href='orderForm.html'
                    }else{
                        alert(r.message)
                    }
                }
            })
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
            if (this.multipleSelection.length<=0){
                $("#price").text(0)
                this.settleBtn=true
                return
            }
            this.settleBtn=false
            let price=0
            for (let i=0;i<this.multipleSelection.length;i++){
                price+=this.multipleSelection[i].spPrice*this.multipleSelection[i].count
                $("#price").text(price)
            }
            console.log(this.multipleSelection)
        },
        deleteRow(index, rows,id) {
            $.ajax({
                url: "/trolley/delete/"+id,
                method: "get",
                success:function (r){
                    if (r.code==410){
                        rows.splice(index, 1);
                        setTimeout(function () {
                            Main.methods.show()
                        },100)

                    }else{
                        alert(r.message)
                    }
                }
            })
        },
        handleChange(value,id) {
            let data={
                value:value,
                id:id
            }
            console.log(data)
            let _this=this
            $.ajax({
                method:"get",
                data:data,
                url:"/trolley/countUpdate",
                success:function (r) {
                    if (r.code==200){
                        _this.showdetail()
                    }else{
                        alert(r.message)
                    }
                }
            })
        },
        showdetail: function () {
            var _this = this
            $.ajax({
                url: "/trolley/shoppingTrolleys",
                method: "get",
                success: function (json) {
                    _this.json = json.data
                }
            })
        }
    },
}
var Ctor = Vue.extend(Mains)
new Ctor().$mount('#details')


var Main = {
    data() {
        return {
            input1: '',
        }
    }
}
var Ctor = Vue.extend(Main)
new Ctor().$mount('#app')


/*/注意：导航 依赖 element 模块，否则无法进行功能性操作*/
layui.use('element', function () {
    var element = layui.element;

    element.on('tab(classify_tab)', function (data) {
        $.ajax({
            type: "get",
            url: "commodityclassify?index=" + data.index,
            success: function (json) {
                $(".layui-tab-content").css("display", "block")
                $("#gogo").css("display", "none")
                v.chg(json)
            }
        })
    });
    //…
});
$('.layui-nav-item>a,dd>a>i,dd>a').hover(function () {
    $(this).css("color", "#cbaf4c")
}, function () {
    $(this).css("color", "#000000")
})
$('.layui-nav-item').eq(0).attr("class", "layui-nav-item layui-this ")