 /**
 * 
 */
$(function(){
	// 从URL里获取shopId参数的值
	let shopId = getQueryString('shopId');
	// 由于店铺注册和修改使用的是同一个页面，该标识符用来标明本次是添加操作还是修改操作
	let isEdit = shopId? true : false;
	
	// 用于店铺注册时候的店铺类别以及区域列表的初始化的URL
	let initUrl = '/o2o/shopadmin/getshopinitinfo';
	// 注册店铺的URL
	let registerShopUrl = "/o2o/shopadmin/registershop";
	
	let shopInfoUrl = '/o2o/shopadmin/getshopbyid?shopId=' + shopId;
	let editShopUrl = '/o2o/shopadmin/updateshop';
	if (!isEdit){
		getShopInitInfo();
	}else{
		getShopInfo(shopId);
	}
	
	function getShopInfo(shopId){
		$.getJSON(shopInfoUrl,function(data){
			if (data.success){
				let shop = data.shop;
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				let shopCategory = '<option data-id="'
					+ shop.shopCategory.shopCategoryId + '"selected>'
					+ shop.shopCategory.shopCategoryName + '</option>';
				let tempAreaHtml = '';
				data.areaList.map(function(item,index){
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
					+ item.areaName + '</option>';
				});
				$('#shop-category').html(shopCategory);
				$('#shop-category').attr('disabled','disabled');
				$('#area').html(tempAreaHtml);
				$("#area option[data-id='"+shop.area.areaId+"']").attr("selected","selected");
			}
		});
	}
	
	function getShopInitInfo(){
		$.getJSON(initUrl,function(data){
			if (data.success){
				let tempHtml = '';
				let tempAreaHtml = '';
				data.shopCategoryList.map(function(item,index){
					tempHtml += '<option data-id="' + item.shopCategoryId + '">'
						+ item.shopCategoryName + '</option>';
				});
				data.areaList.map(function(item,index){
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
					+ item.areaName + '</option>';
				});
				$("#shop-category").html(tempHtml);
				$("#area").html(tempAreaHtml);
			}
		});
	}
	
	$("#submit").click(function(){
		let shop = {};
		if (isEdit) {
			shop.shopId = shopId;
		}
		shop.shopName = $("#shop-name").val();
		shop.shopAddr = $("#shop-addr").val();
		shop.phone = $("#shop-phone").val();
		shop.shopDesc = $("#shop-desc").val();
		shop.shopCategory = {
			shopCategoryId:$("#shop-category").find('option').not(function(){
				return !this.selected;
			}).data("id")
		};
		shop.area = {
			areaId:$("#area").find('option').not(function(){
				return !this.selected;
			}).data("id")	
		};
		let shopImg = $("#shop-img")[0].files[0];
		let formData = new FormData();
		formData.append("shopImg",shopImg);
		formData.append("shopStr",JSON.stringify(shop));	
		let varifyCodeActual = $('#j_captcha').val();
		if (!varifyCodeActual){
			$.toast("请输入验证码");
			return;
		}
		formData.append('varifyCodeActual',varifyCodeActual);
		$.ajax({
			url:(isEdit ? editShopUrl : registerShopUrl),
			type:'POST',
			data:formData,
			contentType:false,
			processData:false,
			cache:false,
			success:function(data){
				if (data.success){
					$.toast("提交成功");
				}else{
					$.toast("提交失败" + data.errMsg);
				}
				$("#captcha_img").click();
			}
		});
	});
});