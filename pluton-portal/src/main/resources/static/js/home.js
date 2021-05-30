let carouselApp = new Vue({
    el: "#carouselApp",
    data: {
        banners: {}
    },
    methods: {
        loadcarousels: function () {
            $.ajax({
                url: "/banner",
                method: "get",
                success: function (data) {
                    console.log("轮播:"+data)
                    carouselApp.banners = data;
                }
            })
        },
        details:function (id) {
            console.log(id)
            document.getElementById("gogo").contentWindow.banner(id);
            setTimeout(function () {
                $("#gogo").css("display", "block")
                $(".layui-tab-content").css("display", "none")
            },100)
        }
    },
    created() {
        this.loadcarousels()
    }
})


function sss() {
    shoppingApp.methods.show()
}


/*/注意：导航 依赖 element 模块，否则无法进行功能性操作*/
layui.use('element', function () {
    var element = layui.element;

    element.on('tab(classify_tab)', function (data) {
        console.log(data.index)
        $.ajax({
            type: "get",
            url: "/commodity/classify/"+data.index,
            success: function (json) {
                v.json=json
                console.log(json)
                $(".layui-tab-content").css("display", "block")
                $("#gogo").css("display", "none")

            }
        })
    });
    //…
});

let v = new Vue({
    el: "#tab",
    data: {
        json: {},
        classifys:{}
    },
    methods: {
        chg() {
            $.ajax({
                type: "get",
                url: "/commodity/popular",
                success: function (json) {
                    console.log(json)
                    v.json=json
                }
            })
        },
        ck: function (id) {
            document.getElementById("gogo").contentWindow.commodyty(id);
            setTimeout(function () {
                $("#gogo").css("display", "block")
                $(".layui-tab-content").css("display", "none")
            },100)
        },
        loadclassify:function () {
            $.ajax({
                type: "get",
                url: "/commodity/classifys",
                success: function (json) {
                    console.log(json)
                    v.classifys=json
                }
            })
        },
    },
    created:function () {
        this.chg();
        this.loadclassify()
    }
})
layui.use('carousel', function () {
    var carousel = layui.carousel;
    //建造实例
    carousel.render({
        elem: '#carousel'
        , width: '100%'
        , height: '550px'
        //设置容器宽度
        , arrow: 'hover' //始终显示箭头
        , anim: 'fade'//切换动画方式
        , interval: 3000
    });
});
