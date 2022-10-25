function group() {
    // グループ情報を取得する
    $.ajax({
        url: BASE_URL + `/group?groupid=${groupId}`,
        type: "GET",
        async: true,
        dataType: "json",
        contentType: "application/json",
        timeout: 5000,
        cache: false,
        success: function (responseData) {
          // 成功時処理
          $('.card-title').text(responseData.groupName);
        },
        error: function  (xhr, textStatus, errorThrown) {
          // 失敗時処理
          window.location.href = "error";
        },
    });


// メンバーの一覧を取得する
$.ajax({
    url: BASE_URL + `/members?groupid=${groupId}`,
    type: "GET",
    async: true,
    dataType: "json",
    contentType: "application/json",
    timeout: 5000,
    cache: false,
    success: function (responseData) {
        // 成功時処理
        var membersList = responseData.members;
        var members;
        for (let i = 0; i < membersList.length; i++) {
            if (i == 0){
                members = membersList[i].memberName + " ";
            } else {
                members = members + membersList[i].memberName + " ";
            }
        }
        $('.members').text(members);
    },
    error: function  (xhr, textStatus, errorThrown) {
        // 失敗時処理
        window.location.href = "error";
        },
    });

// メンバーモーダルの「登録」ボタン押下時の処理
$('#memberReg').on('click', function() {
    let name = document.getElementById('memberName');
    var arr = { groupid: groupId, memberName: name.value };
    var member_info = JSON.stringify(arr);

    $.ajax({
        url: BASE_URL + `/member`,
        type: "POST",
        async: true,
        dataType: "json",
        contentType: "application/json",
        timeout: 5000,
        cache: false,
        data: member_info,
        success: function (responseData) {
          // 成功時処理
          $('#addMember').modal();
          $('#addMember').modal('hide');

          // リロードする
          window.location.reload();
        },
        error: function  (xhr, textStatus, errorThrown) {
          // 失敗時処理
          var res = $.parseJSON(xhr.responseText);
          $('.text-danger').text(res.errorMessage);
        },
    });
});
}