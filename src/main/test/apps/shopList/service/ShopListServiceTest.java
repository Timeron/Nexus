package apps.shopList.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.AssertThrows;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.timeron.NexusDatabaseLibrary.Entity.Product;
import com.timeron.NexusDatabaseLibrary.dao.ProductDAO;
import com.timeron.nexus.apps.shopList.dto.ProductDTO;
import com.timeron.nexus.apps.shopList.service.ShopListService;
import com.timeron.nexus.common.service.ResultMessages;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/simple-job-launcher-context.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DatabaseSetup({ "/dbMock/shopList.xml"})
public class ShopListServiceTest {

	@Autowired
	ShopListService shopListService;
	
	@Autowired
	ProductDAO productDAO;
	
	@Test
	public void getActiveProducts_shouldGetProducts() {
		List<ProductDTO> products = shopListService.getActiveProducts();
		assertEquals(3, products.size());
		assertEquals(1, products.get(0).getId());
		assertEquals(2, products.get(1).getId());
		assertEquals(4, products.get(2).getId());
	}
	
	@Test
	public void saveProduct_shouldSaveNewProduct(){
		ProductDTO product = new ProductDTO();
		product.setBuy(true);
		product.setChecked(null);
		product.setName("newName");
		product.setNumber(5F);
		product.setPriority(4);
		product.setSize("kg");
		
		boolean result = shopListService.saveProduct(product);
		List<ProductDTO> products = shopListService.getActiveProducts();
		
		assertTrue(result);
		assertEquals(4, products.size());
		assertEquals(5, products.get(3).getId());
		assertEquals(product.getChecked(), products.get(3).getChecked());
		assertEquals(product.getName(), products.get(3).getName());
		assertEquals((Float)product.getNumber(), (Float)products.get(3).getNumber());
		assertEquals(product.getPriority(), products.get(3).getPriority());
		assertEquals(product.getSize(), products.get(3).getSize());
		
	}
	
	@Test
	public void updateBuyProduct_setToFalse(){
		ProductDTO product = new ProductDTO();
		product.setId(1);
		product.setBuy(false);
		shopListService.updateBuyProduct(product);
		Product tempProduct = productDAO.getById(1);
		assertEquals(1, tempProduct.getId());
		assertFalse(tempProduct.isBuy());
		assertNotNull(tempProduct.getChecked());
	}
	
	@Test
	public void updateBuyProduct_setToTrue(){
		ProductDTO product = new ProductDTO();
		product.setId(3);
		product.setBuy(true);
		shopListService.updateBuyProduct(product);
		Product tempProduct = productDAO.getById(1);
		assertEquals(1, tempProduct.getId());
		assertTrue(tempProduct.isBuy());
		assertNull(tempProduct.getChecked());
	}

}
