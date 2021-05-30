let classifyApp = new Vue({
    el: "#classifyApp",
    data: {
        classifys: {},
    },
    methods: {
        seek: function (id) {
            console.log(id)
            $.ajax({
                method: "get",
                url: "/commodity/seek/" + id,
                success: function (r) {
                    if (r.code == 200) {
                        $("#mydiv").css("display", "block")
                        $("#gogo").css("display", "none")
                        $("#page").css("display", "none")
                        console.log(r)
                        mydivApp.json = r.data
                    }

                }
            })
        },
        search: function (e) {
            let val = e.target.value
            console.log(val)
            $.ajax({
                url:"/commodity/search",
                method:"get",
                data:{val},
                success:function (r) {
                    console.log(r)
                    if (r.code==200){
                        $("#mydiv").css("display", "block")
                        $("#gogo").css("display", "none")
                        mydivApp.json = r.data
                        $("#page").css("display", "none")
                    }
                }
            })
        },
        loadclassify: function () {
            $.ajax({
                type: "get",
                url: "/commodity/classifys",
                success: function (json) {
                    console.log(json)
                    classifyApp.classifys = json
                }
            })
        },
    },
    created() {
        this.loadclassify()
    }
})


var Main = {
    data() {
        return {
            input1: '',
        }
    }
}
var Ctor = Vue.extend(Main)
new Ctor().$mount('#app')


var mydivApp = new Vue({
    el: "#mydiv",
    data: {json: null},
    methods: {
        chg(json) {
            this.json = json
        },
        ck: function (id) { 
            console.log(id)
            document.getElementById("gogo").contentWindow.commodyty(id);
            setTimeout(function () {
                $("#gogo").css("display", "block")
                $("#mydiv").css("display", "none")
            },100)
        },
    }
});

layui.use('laypage', function () {
    var laypage = layui.laypage;
    $.ajax({
        url: "/commodity/commoditys",
        method: 'get',
        success: function (data) {
            laypage.render({
                elem: 'page' //注意，这里的 test1 是 ID，不用加 # 号
                //数据总数，从服务端得到
                , count: data.count
                , limit: 9
                , jump: function (obj, first) {
                    let page = {
                        pageNum: obj.curr,
                        pagelimit: obj.limit
                    }
                    $.ajax({
                        type: "get",
                        data: page,
                        url: "/commodity/page",
                        success: function (json) {
                            console.log(json)
                            laypage.count = json.total
                            mydivApp.chg(json.list)
                        }
                    })
                    //obj包含了当前分页的所有参数，比如：
                    // console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                    // console.log(obj.limit); //得到每页显示的条数
                    //首次不执行
                    if (!first) {

                    }
                }
            });
        }
    })
})
/*/注意：导航 依赖 element 模块，否则无法进行功能性操作*/
layui.use('element', function () {
    var element = layui.element;

    //…
});
$('.layui-nav-item>.nav_a,dd>a>i,dd>a').hover(function () {
    $(this).css("text-decoration", "none")
    $(this).css("color", "#cbaf4c")
}, function () {
    $(this).css("color", "#000000")
})
$('.layui-nav-item').eq(1).attr("class", "layui-nav-item layui-this ")
