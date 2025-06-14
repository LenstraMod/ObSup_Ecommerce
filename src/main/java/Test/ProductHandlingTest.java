	package Test;
	
	import java.sql.SQLException;
	
	
	import java.util.*;
	
	import com.mysql.cj.protocol.x.SyncFlushDeflaterOutputStream;
	
	import Payment.Payment;
	import Payment.PaymentManager;
	import Product.*;
	import User.*;
	import Utility.InputEmptyException;
	import Utility.MissionUtil;
	
	public class ProductHandlingTest {
		
		static ProductHandler productBiz = new ProductHandler();
		static UserManager userBiz = new UserManager();
		
		public static void ProductHandling() {
			
			productBiz.GetAllProducts();
			
			int menu = 0;
			int menuProduct= 0;
			String thisUserId = UserManager.getThisSessionUser().getUserID();
			
			while(menu != 9) {
				ProductMenu();
				
				String menuPrompt = "Masukkan menu : ";
				
				try {
					menu = MissionUtil.getIntInput(menuPrompt);
					
					if(menu < 0) {
						throw new Exception("Tidak bole angka negatif");
					} 
				}catch (Exception e) {
						System.out.println(e.getMessage());
				}
				
				try {
					switch(menu) {
					case 1 :
						productBiz.ShowProduct();
						break;
						
					case 2:
						ArrayList<Product> formalProducts = productBiz.ShowFormalProduct();
						
						if(formalProducts.isEmpty()) {
							System.out.println("Tidak ada produk yang tersedia");
						}
						else {
							for(Product prd : formalProducts) {
								System.out.println(prd.toString());
								System.out.println();
							}
						}
						break;
						
					case 3:
						ArrayList<Product> nonFormalProducts = productBiz.ShowNonFormalProduct();
						
						if(nonFormalProducts.isEmpty()) {
							System.out.println("Tidak ada produk yang tersedia");
						}
						else {
							for(Product prd : nonFormalProducts) {
								System.out.println(prd.toString());
								System.out.println();
							}
						}
						break;
						
					case 4:
						menuProduct = MissionUtil.getIntInput("Masukkan ID product : ");
						
						String productIdSearch = String.valueOf(menuProduct);
						
						Product findProduct = productBiz.ShowProductDetail(productIdSearch);
						
						if(findProduct != null) {
							System.out.println(findProduct.toString());
							
							System.out.println();
							System.out.println("Mau Ngapain Nih ?");
							System.out.println("1.Beli Product");
							System.out.println("2.Rating Product");
							System.out.println("3.Comment Product");	
							
							int actionMenuProduct = MissionUtil.getIntInput("Pilih Menu : ");
							
							
							
							RegisteredUsers registUser = (RegisteredUsers) UserManager.getThisSessionUser();
							if(registUser.getAddress() == null) {
								System.out.println("Ayo isi alamat mu dulu");
								System.out.print("Alamat/Domisili : ");
								String fillAddress = MissionUtil.getStringInput();
								
								boolean setAddressToDatabase = UserManager.updateAddress(thisUserId, fillAddress);
								
								if(setAddressToDatabase) {
									registUser.setAddress(fillAddress);
									System.out.println("Success memasukkan alamat");
								}
								else {
									System.err.println("Gagal masukkan alamat ke database");
									break;
								}
							}
							
							ActionMenuProductControl(actionMenuProduct,thisUserId,productIdSearch);
						}
						else {
							throw new Exception("ID produk tidak ada");
						}
						break;
						
					case 5:
						ArrayList<Payment> getHistory = History.addHistory(thisUserId);
						
						for(Payment pym : getHistory) {
							pym.paymentDetail();
							System.out.println();
						}
						break;
					case 9:
						System.out.println("Thank you");
						userBiz.logout();
					
				}
				} catch(Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}
		}
		
		static void ActionMenuProductControl(int menuNumber, String userId, String productId) {
			switch(menuNumber) {
			case 1:
				Product getProduct = productBiz.ShowProductDetail(productId);
				
				if(getProduct.getStock() <= 0) {
					System.out.println("Produk habis dan belum restock");
					break;
				}
				
				int getProductAmount = MissionUtil.getIntInput("Masukkan jumlah produk yang ingin dibeli : ");	
				
				if(getProductAmount < 0) {
					throw new IllegalArgumentException("Angka tidak boleh negatif");
				}
				
				double totalPrice = getProduct.getPrice() * getProductAmount;
				
				if(getProduct.getStock() - getProductAmount < 0) {
					System.out.println("Barang hanya sisa " + getProduct.getStock());
					break;
				}
				
				System.out.println();
				System.out.println("Total Harga : " + totalPrice );
				System.out.println("Lanjut beli ?");
				System.out.println("1.Iya");
				System.out.println("2.Tidak");
				
				int continueBuyMenu = MissionUtil.getIntInput("Pilih Menu : ");
				
				switch(continueBuyMenu) {
				case 1:
					
					System.out.println("Pilih Metode Pembayaran");
					System.out.println("1.Kartu Debit/Kredit");
					System.out.println("2.E-Wallet");
					System.out.println("3.Cash on Delivery");
					
					int paymentMethodMenu = MissionUtil.getIntInput("Pilih metode pembayaran : ");
					
					try {
						switch(paymentMethodMenu) {
						case 1:
							System.out.println("Isi Data Kartu Dibawah Ini : ");
							System.out.println();
							System.out.print("Masukkan nomor kartu : ");
							String cardNumber = MissionUtil.getStringInput();
							System.out.print("Masukkan expired date kartu : ");
							String expiredDate = MissionUtil.getStringInput();
							System.out.print("Masukkan cvv : ");
							String cvv = MissionUtil.getStringInput();
							
							boolean getPaymentStatus = PaymentManager.cardPaymentProcess(cardNumber, expiredDate, cvv, userId, productId,totalPrice);
							
							if(getPaymentStatus) {
								int productAmountNow = getProduct.getStock() - getProductAmount;
								productBiz.updateStock(productId, productAmountNow);
								System.out.println("Checkout Success");
							} else {
								System.out.println("Checkout Failed");
							}
							break;
							
						case 2:
							System.out.println();
							System.out.println("Pilih E-wallet yang kamu gunakan : ");
							System.out.println("1.GoPey");
							System.out.println("2.E-Duit");
							System.out.println("3.ShoppeCash");
							
							int EWalletMenu = MissionUtil.getIntInput("Pilih Menu : ");
							
							System.out.print("No telepon E-Wallet anda : ");
							String phoneNumberEWallet = MissionUtil.getStringInput();
							
							try {
								switch(EWalletMenu) {
								
								case 1:
									
									boolean getPaymentStatusGoPey = PaymentManager.EWalletPaymentProcess("GoPey",phoneNumberEWallet,userId,productId,totalPrice);
									
									if(getPaymentStatusGoPey) {
										System.out.println("Checkout berhasil");
									}
									else {
										System.out.println("Checkout gagal");
									}
									break;
									
								case 2:
	
									boolean getPaymentStatusEDuit = PaymentManager.EWalletPaymentProcess("E-Duit",phoneNumberEWallet,userId,productId,totalPrice);
									
									if(getPaymentStatusEDuit) {
										System.out.println("Checkout berhasil");
									}
									else {
										System.out.println("Checkout gagal");
									}
									break;
								case 3:
	
									boolean getPaymentStatusShopeeCash = PaymentManager.EWalletPaymentProcess("ShopeeCash",phoneNumberEWallet,userId,productId,totalPrice);
									
									if(getPaymentStatusShopeeCash) {
										System.out.println("Checkout berhasil");
									}
									else {
										System.out.println("Checkout gagal");
									}
									break;
								}
							} catch(Exception e) {
								e.printStackTrace();
								System.out.println("EWallet Payment Error : " + e.getMessage());
							}
							
							break;
							
						case 3:
							System.out.println("Masukka no telepon yang bisa dihubungi!");
							System.out.print("No Telepon : ");
							String phoneNumber = MissionUtil.getStringInput();
							boolean getPaymentStatusCOD = PaymentManager.cashOnDeliveryProcess(userId, productId, phoneNumber, totalPrice);
							
							if(getPaymentStatusCOD) {
								int productAmountNow = getProduct.getStock() - getProductAmount;
								productBiz.updateStock(productId, productAmountNow);
								System.out.println("Checkout Success");
							} else {
								System.out.println("Checkout Failed");
							}
							break;
						}
					} catch(IllegalArgumentException e) {
						e.printStackTrace();
						System.err.println(e.getMessage());
					}catch(Exception e) {
						e.printStackTrace();
						System.err.println(e.getMessage());
					} 	
				}
				break;
				
			case 2:
				System.out.println();
				System.out.println("Rating : " + UserManager.ratingCalculation(productId));
				
				System.out.println("Ingin kasih rating ?");
				System.out.println("1.Kasih rating");
				System.out.println("2.Kembali");
				
				int ratingMenu = MissionUtil.getIntInput("Pilih Menu : ");
				
				try {
					switch(ratingMenu) {
					case 1:
						 System.out.println();
						 int ratingValue = MissionUtil.getIntInput("Masukkan Rating : ");
						 
						 boolean getRatingStatus = UserManager.addRating(ratingValue, productId);
						 
						 if(getRatingStatus) {
							 System.out.println("Rating Berhasil");
							 break;
						 }
						 else {
							 throw new Exception("Rating gagal");
						 }
					case 2:
						break;
					}
				} catch (InputEmptyException e) {
					e.printStackTrace();
					System.out.println("Rating Error : " + e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Rating Error : " + e.getMessage());
				}
				break;
				
			case 3:
				UserManager.getComment();
				
				ArrayList<Comment> allComments = UserManager.getComments();
				
				if(allComments.isEmpty()) {
					System.out.println("Tidak ada data komen");
				}
				else {
					for(Comment cm : UserManager.getComments()) {
						if(cm.getProductId().equals(productId)) {
							User getUser = userBiz.getUser(cm.getUserId());
							System.out.println("User : " + getUser.getUsername() + "\n" +cm.toString());
							System.out.println();
						}
					}
				}
				
				System.out.println("Mau komen?");
				System.out.println("1.Komen produk");
				System.out.println("2.Kembali");
				
				int menuComment = MissionUtil.getIntInput("Pilih menu : ");
				
				try {
					switch(menuComment) {
					case 1:
						System.out.print("Komen : ");
						String commentText = MissionUtil.getStringInput();
						
						boolean getCommentStatus = UserManager.addComment(commentText, userId, productId);
						
						if(getCommentStatus) {
							System.out.println("Komen berhasil di tambahkan");
						}
						else {
							throw new Exception("Gagal Komen");
						}
						break;
					
					case 2:
						break;
					
					}
					
					break;
				} catch (InputEmptyException e){
					e.printStackTrace();
					System.out.println("Komen Error : " + e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Komen Error : " + e.getMessage());
				}
		}
	}
		
		public static void AdminProductHandling() {
			productBiz.GetAllProducts();
			
			int menu = 0;
			while(menu != 9) {
				try {
				
					AdminMenu();
					
					menu = MissionUtil.getIntInput("Pilih Menu : " );
					switch(menu) {
					case 1:
						System.out.println("Masukkan Produk Baru");
						System.out.print("Masukkan Nama Product : ");
						String productName = MissionUtil.getStringInput();
						
						System.out.print("Masukkan ukuran produk : ");
						String productSize = MissionUtil.getStringInput();
						
						System.out.print("Masukka warna produk : ");
						String productColor = MissionUtil.getStringInput();
						
						System.out.println("Masukkan kategori produk");
						System.out.println("1.Produk Formal");
						System.out.println("2.Produk Casual");
						int categoryMenu = 0;
						String productCategory = null;
						while(!(categoryMenu == 1) || !(categoryMenu == 2)) {
							categoryMenu = MissionUtil.getIntInput("Pilih Menu : ");
						
							if(categoryMenu == 1) {
								productCategory = "Formal Product";
								break;
							}
							else if(categoryMenu == 2) {
								productCategory = "Non-Formal Product";
								break;
							}
							else {
								System.out.println("Wajib Masukkan Category Produk");
							}
						}
						
						System.out.print("Masukkan deskripsi produk : ");
						String productDescription = MissionUtil.getStringInput();
						
						int stock = MissionUtil.getIntInput("Masukkan stok produk : ");
						
						double price = MissionUtil.getIntInput("Masukkan harga produk : ");
						
						if(categoryMenu == 1) {
							System.out.print("Masukkan dresscode produk : ");
							String dresscode = MissionUtil.getStringInput();
							
							System.out.print("Masukkan material produk produk : ");
							String material = MissionUtil.getStringInput();
							
							boolean getAddStatus = productBiz.InsertProduct(productName, productSize, productColor, productCategory, productDescription, stock, price, dresscode, material);
							
							if(getAddStatus) {
								System.out.println("Berhasil menambahkan produk");
							} else {
								throw new Exception("Gagal menambah produk");
							}
							
						} else if(categoryMenu == 2) {
							System.out.print("Masukkan ocassion produk : ");
							String ocassion = MissionUtil.getStringInput();
							
							boolean getAddStatus = productBiz.InsertProduct(productName, productSize, productColor, productCategory, productDescription, stock, price, ocassion);
							
							if(getAddStatus) {
								System.out.println("Berhasil menambahkan produk");
								productBiz.GetAllProducts();
							} else {
								throw new Exception("Gagal menambah produk");
							}
						}
						
						break;
					case 2:
						 
						System.out.print("Masukkan produk ID : ");
						String getProductId = MissionUtil.getStringInput();
						
						Product findProduct = productBiz.ShowProductDetail(getProductId);
						String propertyUpdate = null;
						String newValue = null;
						
						if(findProduct != null) {
							System.out.println(findProduct.toString());
							
							System.out.println("Pilih bagian produk yang ingin diedit ?");
							System.out.println("1.Nama Produk");
							System.out.println("2.Ukuran Produk");
							System.out.println("3.Warna Produk");
							System.out.println("4.Kategori Produk");
							System.out.println("5.Deskripsi Produk");
							System.out.println("6.Stok Produk");
							System.out.println("7.Harga Produk");
							if(findProduct.getProductCategory().equals("Formal Product")) {
								System.out.println("8.Dresscode Produk");
								System.out.println("9.Material Produl");
							}
							else {
								System.out.println("8.Ocassion Produk");
							}
							
							try {
								int menuEditProduct = MissionUtil.getIntInput("Pilih Menu : ");
								
								switch(menuEditProduct) {
								case 1:
									System.out.print("Masukkan nama produk yang baru : ");
									propertyUpdate = "productName";
									newValue = MissionUtil.getStringInput();
									break;
								
								case 2:
									System.out.print("Masukkan ukuran produk yang baru : ");
									propertyUpdate = "productSize";
									newValue = MissionUtil.getStringInput();
									break;
								
								case 3:
									System.out.print("Masukkan warna produk yang baru : ");
									propertyUpdate = "productColor";
									newValue = MissionUtil.getStringInput();
									break;
									
								case 4:
									System.out.print("Masukkan kategori produk yang baru : ");
									propertyUpdate = "productCategory";
									newValue = MissionUtil.getStringInput();
									break;
								
								case 5:
									System.out.print("Masukkan deskripsi produk yang baru : ");
									propertyUpdate = "productDescription";
									newValue = MissionUtil.getStringInput();
									break;
								case 6:
									System.out.print("Masukkan stok produk yang baru : ");
									propertyUpdate = "stock";
									newValue = MissionUtil.getStringInput();
									break;
								case 7:
									System.out.print("Masukkan harga produk yang baru : ");
									propertyUpdate = "price";
									newValue = MissionUtil.getStringInput();
									break;
								case 8:
									if(findProduct.getProductCategory().equals("Formal Product")) {
										System.out.print("Masukkan dresscode produk yang baru : ");
										propertyUpdate = "dresscode";
										newValue = MissionUtil.getStringInput();
										break;
									}
									else {
										System.out.print("Masukkan occasion produk yang baru : ");
										propertyUpdate = "ocassion";
										newValue = MissionUtil.getStringInput();
										break;
									}
									
								case 9:
									if(findProduct.getProductCategory().equals("Formal Product")) {
										System.out.print("Masukkan material produk yang baru : ");
										propertyUpdate = "material";
										newValue = MissionUtil.getStringInput();
										break;
									}
									else {
										throw new Exception("Pilih nomor menu yang sesuai");
									}
									
								}
							} catch(InputEmptyException e) {
								e.printStackTrace();
								System.out.println("Add Product Error :" + e.getMessage());
							} catch(Exception e) {
								e.printStackTrace();
								System.out.println("Add Product Error :" + e.getMessage());
							}
							
							boolean getUpdateStatus = productBiz.UpdateProduct(getProductId, propertyUpdate, newValue);
							
							if(getUpdateStatus) {
								System.out.println("Data berhasil diupdate");
								productBiz.GetAllProducts();
							}
							else {
								throw new Exception("Data gagal diupdate");
							}
							
						} else {
							throw new Exception("ID Product tidak ada");
						}
						break;
					
					case 3:
						System.out.print("Masukkan produk ID : ");
						String getProductIdDelete = MissionUtil.getStringInput();
						
						Product findProductDelete = productBiz.ShowProductDetail(getProductIdDelete);
						
						if(findProductDelete != null) {
							productBiz.DeleteProduct(getProductIdDelete);
							productBiz.GetAllProducts();
						} else {
							throw new Exception("ID produk tidak ada");
						}
						break;
					case 9:
						userBiz.logout();
						break;
				}
					
					
				} catch(InputEmptyException e) {
					e.printStackTrace();
					System.out.println("Add Product Error :" + e.getMessage());
				} catch(Exception e) {
					e.printStackTrace();
					System.out.println("Add Product Error :" + e.getMessage());
				}
			}
		}
		
		static void ProductMenu() {
			System.out.println("---------- Pilih Menu ----------");
			System.out.println("1.Tunjukkan Semua Product");
			System.out.println("2.Tunjukkan Semua Product Formal");
			System.out.println("3.Tunjukkan Semua Product Non Formal");
			System.out.println("4.Check Product");
			System.out.println("5.Check history pembelian");
			System.out.println("9.Log out");
	
		}
		
		static void AdminMenu() {
			System.out.println("---------- Pilih Menu ----------");
			System.out.println("1.Tambah Produk");
			System.out.println("2.Edit Produk");
			System.out.println("3.Delete Produk");
			System.out.println("9.Log out");
		}
		
	}
