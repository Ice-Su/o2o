$(function(){
	// 获取此店铺下的商品列表的URL
	let listUrl = '/o2o/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=999';
	// 商品下架URL
	let statusUrl = '/o2o/shopadmin/updateproduct';
	getList();
	/**
	 * 获取某个店铺下的商品列表
	 */
	function getList() {
		// 从后台获取此店铺的商品列表
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				let productList = data.productList;
				let tempHtml = '';
				// 遍历每条商品信息，拼接成一行显示，列信息包括：
				// 商品名称，优先级， 上架/下架(含productId),编辑按钮(含productId)
				// 预览(含productId)
				productList.map(function(item,index) {
					let textOp = '下架';
					let contraryStatus = 0;
					if (item.enableStatus == 0) {
						// 若状态值为0，表明该商品已经是下架状态，能进行的操作是 上架
						textOp = '上架';
						contraryStatus = 1;
					} else {
						contraryStatus = 0;
					}
					// 拼接每件商品的行信息
					tempHtml += '' + '<div class="row row-product">'
							+ '<div class="col-33">'
							+ item.productName
							+ '</div>'
							+ '<div class="col-20">'
							+ item.priority
							+ '</div>'
							+ '<div class="col-40">'
							+ '<a href="#" class="edit" data-id="'
							+ item.productId
							+ '" data-status="'
							+ item.enableStatus
							+ '">编辑</a>'
							+ '<a href="#" class="status" data-id="'
							+ item.productId
							+ '"data-status="'
							+ contraryStatus
							+ '">'
							+ textOp
							+ '</a>'
							+ '<a href="#" class="preview" data-id="'
							+ item.productId
							+ '">预览</a>'
							+ '</div>'
							+ '</div>';
				});
				// 将拼接好的信息复制给html空间中
				$('.product-wrap').html(tempHtml);
			}
		});
	}
	
	// 将class 为product-wrap里面的a标签绑定上点击事件
	$('.product-wrap').on('click','a',function(e) {
		let target = $(e.currentTarget);
		if (target.hasClass('edit')) {
			// 如果a标签class 有edit则进入商品信息编辑界面，并带有productId参数
			window.location.href= '/o2o/shopadmin/productoperation?productId='
					+ e.currentTarget.dataset.id;
		} else if (target.hasClass('status')) {
			// 如果a标签class 有status则调用后台上下级相关商品，并带有productId参数
			changeItemStatus(e.currentTarget.dataset.id,
						e.currentTarget.dataset.status);
		} else if (target.hasClass('preview')) {
			// 如果class  preview 则去前台展示系统该商品详情页预览商品情况
			window.location.href = '/o2o/frontend/productdetail?productId='
					+ e.currentTarget.dataset.id;
		}
	});
	
	function changeItemStatus(id,status) {
		// 定义product json对象并添加productId以及状态(上架/下架)
		let product = {};
		product.productId = id;
		product.enableStatus = status;
		$.confirm('确定吗?',function() {
			// 上下架相关商品
			$.ajax({
				url: statusUrl,
				type: 'POST',
				data: {
					productStr: JSON.stringify(product),
					statusChange: true
				},
				dataType: 'json',
				success: function(data) {
					if (data.success) {
						$.toast('操作成功!');
						getList();
					} else {
						$.toast('操作失敗');
					}
				}
			});
		})
	}
});