let userApp=new Vue({
    el:"#userApp",
    data:{
        show:false,
        user:{}
    },
    methods:{
        loadUser:function () {
            $.ajax({
                url:"/user/my",
                method:"get",
                success:function (r) {
                    console.log(r)
                    if (r.code==200){
                        userApp.user=r.data
                        console.log(userApp.user)
                        userApp.show=true
                    }else if (r.code==401){
                        layui.use('layer', function () {
                            userApp.show=true
                            var layer = layui.layer;
                            layer.open({
                                title: "个人信息",
                                type: 2,
                                area: ['480px', '400px'],
                                fixed: false, //不固定
                                offset: 'auto',
                                closeBtn: 0,
                                maxmin: true,
                                content: 'personal.html'
                            });
                        })
                    }else{
                        userApp.show=false
                    }
                }
            })
        },
        management:function () {
            $.ajax({
                url:"/management",
                method:"get",
                success:function (r) {
                    if (r.code==200){
                        window.location.href="management.html"
                    }else{
                        layui.use('layer', function () {
                           var layer=layui.layer
                            layer.msg(r.message)
                        });
                    }
                }
            })
        }
    },
    created:function () {
        this.loadUser();
    }
})

var shoppingApp = {
    data() {
        return {
            gridData: {},
            shows: false,
            block: false,
            userShow: false,
        }
    },
    created() {
        this.show()
    },
    methods: {
        login: function () {
            window.location.href = 'login.html'
        },
        show: function () {
            var _this = this
            $.ajax({
                url: "/trolley",
                type: "get",
                success: function (json) {
                    if (json.code == 200) {
                        _this.gridData = json.data
                        console.log(_this.gridData)
                        if (_this.gridData == '') {
                            _this.gridData.length=0
                            _this.shows = true
                            _this.block = false;
                        } else {
                            _this.block = true;
                            _this.shows = false
                            $(".el-badge__content").text(_this.gridData.length)
                        }

                        this.userShow = false;
                    } else {
                        _this.userShow = true;
                    }
                }
            })
        },
        location: function () {
            window.location.href = 'shoppingdetail.html'
        },
        removerByid:function (id) {
            console.log(this.gridData)
            console.log(id);
            for (let i=0;i<this.gridData.length;i++){
                if (this.gridData[i].id==id){
                    console.log(this.gridData[i].id)
                }
            }
        },
        deleteById: function (id) {
            let _this = this
            $.ajax({
                url: "/trolley/delete/"+id,
                method: "get",
                success: function (r) {
                    if (r.code==410){
                        if (_this.gridData == '') {
                            _this.shows = true
                            _this.block = false;
                        }
                        setTimeout(function () {
                            _this.show();
                        }, 50)
                    }else{
                        alert(r.message)
                    }
                }
            })
        }
    },
};
var Ctor = Vue.extend(shoppingApp)
new Ctor().$mount('#shopping')

var Mains = {
    data() {
        return {
            input1: '',
        }
    }
}
var Ctor1 = Vue.extend(Mains)
new Ctor1().$mount('#app')

$('.layui-nav-item>a,dd>a>i,dd>a').hover(function () {
    $(this).css("color", "#cbaf4c")
}, function () {
    $(this).css("color", "#000000")
})
