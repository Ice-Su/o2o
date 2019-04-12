/**
 * 
 */
$(function() {
	let loading = false;
	// 分页允许返回的最大条数, 超过此数则禁止访问后台
	let maxItems = 999;
	// 一页返回的最大条数
	let pageSize = 3;
	// 获取商店列表的URL
	let listUrl = '/o2o/frontend/listproductsbyshop';
	// 页码
	let pageNum = 1;
	// 从地址栏URL里尝试获取shopId .
	let shopId = getQueryString('shopId');
	let productCategoryId = '';
	let productName = '';
	// 获取店铺类别列表以及区域列表的URL
	let searchDivUrl = '/o2o/frontend/listshopdetailpageinfo?shopId=' + shopId;
	// 渲染出店铺类别列表以及区域列表以供搜索
	getSearchDivData();
	// 预先加载10条店铺信息
	addItems(pageSize,pageNum);
	
	// 给兑换里品的a标签赋值兑换礼品的URL
	//$('#exchangelist').attr('href', '/o2o/frontend/awardlist?shopId=' + shopId);
	/**
	 * 获取本店铺信息以及商品类别信息列表
	 */
	function getSearchDivData() {
		let url = searchDivUrl;
		$.getJSON(url, function(data) {
			if (data.success) {
				// 获取后台返回过来的店铺信息
				let shop = data.shop;
				$('#shop-cover-pic').attr('src',shop.shopImg);
				$('#shop-update-time').html(new Date(shop.updateTime).Format("yyyy-MM-dd"));
				$('#shop-name').html(shop.shopName);
				$('#shop-desc').html(shop.shopDesc);
				$('#shop-addr').html(shop.shopAddr);
				$('#shop-phone').html(shop.phone);
				// 获取后台返回的该店铺的商品类别列表
				let productCategoryList = data.productCategoryList;
				let html = '';
				html += '<a href="#" class="button col-33" data-category-id="">全部类别</a>';
				// 遍历店铺类别列表, 拼接出a标签集
				productCategoryList.map(function(item, index) {
					html += '<a href="#" class="button col-33" data-category-id='
							+ item.productCategoryId
							+ '>'
							+ item.productCategoryName
							+ '</a>';
				});
				$('#shopdetail-button-div').html(html);
			}
		});
	}
	
	/**
	 * 获取分页展示的店铺列表信息
	 * 
	 * @param pageSize
	 * @param pageIndex
	 */
	function addItems(pageSize, pageIndex) {
		// 拼接从护查询的URL, 赋空值默认就去掉这个条件的限制, 有值就代表按这个条件去查询
		let url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize=' + pageSize
				+ '&shopId=' + shopId + '&productCategoryId=' + productCategoryId + '&productName=' + productName;
		// 设定加载符, 若还在后台取数据则不能再次访问后台, 避免多次重复加载
		loading = true;
		// 访问后台获取相应查询条件下的商品列表
		$.getJSON(url, function(data) {
			if (data.success) {
				// 获取当前查询条件下商品的总数
				maxItems = data.count;
				let html = '';
				// 遍历商品列表, 拼接出卡片集合
				data.productList.map(function(item, index) {
					html += '' + '<div class="card" data-product-id="'
							+ item.productId + '">' + '<div class="card-header">'
							+ item.productName + '</div>'
							+ '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ item.imgAddr + '" width="44"' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.productDesc
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<p class="color-grat">'
							+ new Date(item.updateTime).Format("yyyy-MM-dd")
							+ '更新</p>' + '<span>点击查看</span>' + '</div>'
							+ '</div>';
				});
				// 将卡片集合添加到目标HTML组件中
				$('.list-div').append(html);
				// 获取目前为止已显示的卡片总数, 包含之前已经加载的
				let total = $('.list-div .card').length;
				// 若总数达到跟按照此查询条件列出来的总数一致, 则停止后台的加载
				if (total >= maxItems) {
					// 加载完毕, 则注销无限加载事件, 以防不必要的加载
//					$.detachInfiniteScroll($('.infinite-scroll'));
					// 删除加载提示符
					$('.infinite-scroll-preloader').hide();
				} else {
					$('.infinite-scroll-preloader').show();
				}
				// 否则页码加1, 继续load出新的店铺
				pageNum += 1;
				// 加载结束, 可以再次加载
				loading = false;
				// 刷新页面, 显示新加载的店铺
				$.refreshScroller();
			}
		});
	}
	
	// 下滑屏幕自动进行分页搜索
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading) {
			return;
		}
		addItems(pageSize,pageNum);
	});
	
	// 点击店铺的卡片进入该店铺的详情页
	$('.list-div').on('click', '.card', function(e) {
		let productId = e.currentTarget.dataset.productId;
		window.location.href = '/o2o/frontend/productdetail?productId=' + productId;
	});
	
	// 选择新的商品类别后, 重置页码, 清空原先的店铺列表, 按照新的类别去查询
	$('#shopdetail-button-div').on('click', '.button', function(e) {
		// 获取商品类别Id
		productCategoryId = e.target.dataset.categoryId;
//		if (productCategoryId) {
			// 若之前已经选定了别的category, 则移除其选定效果, 改成选定新的
			if ($(e.target).hasClass('button-fill')) {
				$(e.target).removeClass('button-fill');
				shopCategoryId = '';
			} else {
				$(e.target).addClass('button-fill').siblings().removeClass('button-fill');
			}
			// 由于查询条件改变, 清空店铺列表再查询
			$('.list-div').empty();
			// 重置页码
			pageNum = 1;
			addItems(pageSize,pageNum);
//		}
		
//		$.attachInfiniteScroll($('.infinite-scroll'));
	});
	
	// 需要查询的商品名字发生改变后, 重置页码, 清空原先的商品列表, 按照新的名字再进行查询
	$('#search').on('change', function(e) {
		productName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
//		$.attachInfiniteScroll($('.infinite-scroll'));
	});
	
	// 点击后打开右侧栏
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});
	
	// 初始化页面
	$.init();
});