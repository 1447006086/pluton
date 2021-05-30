layui.use(['element', 'rate', 'layedit', 'form', 'layer'], function () {
    var element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
    var layer = layui.layer;
    var rate = layui.rate;
    var layedit = layui.layedit;
    var form = layui.form;
    var index = layedit.build('comment', {
        height: 200,
        tool: ['face', '|']
    }); //建立编辑器

    form.on('submit(*)', function () {
        let content = layedit.getContent(index)
        if (!content) {
            layer.msg("请输入内容!", {icon: 5});
        } else {
            let form = {
                content: content,
                spId: shoppingApp.json.id,
                rate: instance.config.value
            }
            console.log(form)
            $.ajax({
                url: "/comment/add",
                type: "post",
                data: form,
                success: function (r) {
                    if (r.code == 201) {
                        commentApp.addComment(r.data)
                        layer.msg("发布成功", {icon: 6})
                        index = layedit.build('comment', {
                            height: 200,
                            tool: ['face', '|']
                        });
                    } else {
                        layer.msg(r.message, {icon: 5})
                    }
                }
            })
        }
        return false
    });

    var user = rate.render({
        elem: '#userRate'
        , value: $("userRate").val() //初始值
        , half: true //开启半星
        , theme: '#009688'
    })

    rate.render({
        elem: '#allRate'
        , value: 3.5
        , half: true //开启半星
        , readonly: true
        , setText: function (value) { //自定义文本的回调
            $(".rateText").text(value)
        }
    })
    var instance = rate.render({
        elem: '#rate'
        , value: 0
        , half: true
        , text: true
        , setText: function (value) { //自定义文本的回调
            var arrs = {
                '0': ''
                , '1': '极差'
                , '2': '差劲'
                , '3': '中等'
                , '4': '满意'
                , '5': '极好'
            };
            this.span.text(arrs[value]);
            this.span.css("font-size", "16px")
        }
    })


})
var dataSp;

function commodyty(id) {
    $.ajax({
        type: "get",
        url: "/commodity/datails/" + id,
        success: function (r) {
            if (r.code = 200) {
                shoppingApp.chg(r.data)
                dataSp = r.data
            }
        }
    })
    commentApp.loadcomment(id)
}
function banner(id) {
    $.ajax({
        type: "get",
        url: "/banner/details/" + id,
        success: function (r) {
            console.log(r)
            if (r.code = 200) {
                shoppingApp.chg(r.data)
                dataSp = r.data
            }
        }
    })
    commentApp.loadcomment(id)
}

let shoppingApp = new Vue({
    el: "#shoppingApp",
    data: {json: {}},
    methods: {
        chg(json) {
            this.json = json
        }
    }
})

let commentApp = new Vue({
    el: "#commentApp",
    data: {
        comments: {},
        user: null
    },
    methods: {
        removerComment: function (commentId, index) {
            if (!commentId) {
                layui.use('layer', function () {
                    var layer = layui.layer;
                    layer.msg("删除评论发生错误", {icon: 5})
                });
                return
            }

            let id = commentId
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.confirm("删除该条评论?", {
                    icon: 6,
                    shadeClose: true,
                    shade: false,
                }, function () {
                    $.ajax({
                        url: "/comment/delete/" + id,
                        mehtod: "get",
                        success: function (r) {
                            if (r.code = 410) {
                                commentApp.comments.splice(index, 1)
                                layer.msg(r.message, {icon: 6})

                            } else {
                                layer.msg(r.message, {icon: 5})
                            }
                        }
                    })
                })
            });

        },
        addComment: function (comment) {
            comment.duration = "刚刚"
            this.comments.unshift(comment)
            $('.layui-btn-sm').next().toggle(200)
        },
        loadcomment: function (id) {
            $.ajax({
                url: "/comment/" + id,
                method: "get",
                success: function (r) {
                    if (r.code == 200) {
                        commentApp.comments = r.data;
                        commentApp.updateDateTime(commentApp.comments)
                    } else {
                        alert(r.message)
                    }
                }
            })
        },
        loadUser: function () {
            $.ajax({
                url: "/user/my",
                method: "get",
                success: function (r) {
                    if (r.code = 200) {
                        commentApp.user = r.data;
                        console.log(commentApp.user)
                    } else {
                        alert("服务器忙")
                    }
                }
            })
        },
        updateDateTime: function () {
            let comment = this.comments
            for (let i = 0; i < comment.length; i++) {
                let createTime = new Date(comment[i].dateTime).getTime()
                let now = new Date().getTime()
                let duration = now - createTime;
                if (duration < 1000 * 60) { //一分钟以内
                    comment[i].duration = "刚刚";
                } else if (duration < 1000 * 60 * 60) { //一小时以内
                    comment[i].duration =
                        (duration / 1000 / 60).toFixed(0) + "分钟前";
                } else if (duration < 1000 * 60 * 60 * 24) {
                    comment[i].duration =
                        (duration / 1000 / 60 / 60).toFixed(0) + "小时前";
                } else {
                    comment[i].duration =
                        (duration / 1000 / 60 / 60 / 24).toFixed(0) + "天以前";
                }
                console.log(comment[i].duration)
            }
        }
    },
    created: function () {
        this.loadUser();
    }
})

//计数器
var Main = {
    data() {
        return {
            num: 1
        };
    },
    methods: {
        handleChange(value) {
            count = value
        }
    }
};
var Ctor = Vue.extend(Main)
new Ctor().$mount('#add')

$("#shopping").click(function () {
    let data={
        spId:dataSp.id,
        spName:dataSp.title,
        count:$(".el-input__inner").val(),
        spPrice:dataSp.price,
        url: dataSp.url
    }
    console.log(data)
    $.ajax({
        type: "get",
        data:data,
        url: "/trolley/add",
        success: function (json) {
            console.log(json)
            if (json.code == 200) {
                layui.use('layer', function () {
                    var layer = layui.layer;
                    layer.msg('已加入购物车', {icon: 1, anim: 1,offset:[145]});
                    parent.sss()
                });

            } else {
                parent.location.href = 'login.html'
            }
        }
    })
})