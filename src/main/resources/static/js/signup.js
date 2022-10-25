function signup() {
    let name = document.getElementById('inputGroupName').value;
    var arr = { groupname: name };
    var login_info = JSON.stringify(arr);
    $.ajax({
        url: BASE_URL + "/signup",
        type: "POST",
        async: true,
        dataType: "json",
        contentType: "application/json",
        timeout: 5000,
        cache: false,
        data: login_info,
        success: function (responseData) {
            // 成功時処理
            window.location.href = "group?id=" + responseData.groupId;
        },
        error: function (xhr, textStatus, errorThrown) {
            // 失敗時処理
            var res = $.parseJSON(xhr.responseText);
            $('.text-danger').text(res.errorMessage);
            window.location.reload;
        },
    });
}