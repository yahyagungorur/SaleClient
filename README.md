# SaleClient
Android Client
C Server : https://github.com/yahyagungorur/SaleServer

SaleClient.apk and SaleServer.exe
   This is a client sale applicaiton, which works syncronized with SaleServer application. 
   - Sale server satisfies products to client 
   - Sale client do sales and uploads results
   - Sale server saves uploaded sales

SaleClient.apk (in java language - android)
1 - When program started, ask ip and port to be connected 
2 - Then if user clicks "connect" button, try to connect Sale server. 
	2.1 if connection failed, give error message
	2.1 if connection is satisfied, go synronization page
3 - On syncronization page, there should be options to download products and to upload results
	3.1 if download products selected , send "get product" message to Sale server, and get product list
              Sale server returns "product name, vat rate and unit price" in response message
	3.2 if upload sales selected, send "sale details" message to Sale server
              "product name, total quantity and total price" are sent to Sale server by "sale details" message
	3.3 if next button clicked, go Sale page
4 - On Sale page, list the downloaded products and sale them.
	4.1 when user clicked any item, show item price on top, and total amount on top.
		when payment (CASH or CREDIT) is done, show totaland save sale details.
		for example downloaded products are "APPLE 2.00TL", "CHERRY 2.25TL", "WATER 0.75TL", etc
                assume firstly APPLE is clicked, message
                             APPLE            2.00TL
                             TOTAL            2.00TL
                then WATER is clicked, message
                             WATER            0.75TL
                             TOTAL            2.75TL
                then APPLE is clicked again, message
                             APPLE            2.00TL
                             TOTAL            4.75TL
                then CASH is clicked
                             CASH             4.75TL
                             PAYMENT HAS DONE...

	4.2 after payment is done save it. and if user press again sale items, start new sale
            when payment is done, add this data to previous ones.
            in this example, first receipt close APPLE 4.00TL, WATER 0.75TL and TOTAL 4.75TL and CASH 4.75TL
            if second receipt with items of APPLE 2.00TL, WATER 1.50TL, CHERRY 2.25TL and TOTAL 5.75TL and CREDIT 5.75TL
            if third receipt with items of WATER 0.75TL, CHERRY 3.75TL and TOTAL 4.50TL and CREDIT 4.50TL
            after these three receipt, 
		sale data are as followings -> APPLE : 6.00TL         CHERRY : 6.00TL        WATER : 3.00TL 
		payments  are as followings -> CASH  : 4.75TL         CREDIT :10.25TL   

	4.3 if previous button clicked, go Syncronization page 

5 - On Syncronization page, sales above can be uploaded to Sale server
    Server saves sales.

SaleServer.exe (in C programming language)

1 - SaleServer has SQLite database for products and sale results. It has 3 tables;
    Product table has "Id, ProductName, VatRate, UnitPrice" columns
    Sales table has "ReceiptCount, TotalAmount, CashPayment, CreditPayment" columns
    SaleDetails table has "ProductId, ProductName, Amount" columns

    for above example, data of product table as followings
         Id       ProductName       VatRate        UnitPrice
    ---------     -----------     -----------     -----------
         1          APPLE            8               2.00
         2          CHERRY           8               2.25
         3          WATER            1               0.75

    
2 - Displays ip and port to be connected
3 - If any message receives is received,
	2.1 decide type of message (for example message code 1-download products 2-upload sale results)
	2.2 if download products message received, send products to client
	2.2 if upload sales message received, receive sales and save them to database

	    for above example, data of Sale table as followings
	    
    ReceiptCount    TotalAmount     CashPayment     CreditPayment
    -----------     -----------     -----------     -----------
        3              15.00           4.75           10.25

             and data of SaleDetails table as followings

      ProductId     ProductName       Amount
    -----------     -----------     -----------   
        1            APPLE           6.00       
        2            CHERRY          6.00
        3            WATER           3.00
            
