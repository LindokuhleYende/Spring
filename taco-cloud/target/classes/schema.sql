-- 🧾 Create Taco_Order table to store customer orders
create table if not exists Taco_Order (
                                          id identity, -- Auto-generated primary key
                                          delivery_Name varchar(50) not null, -- Recipient's name
    delivery_Street varchar(50) not null, -- Street address
    delivery_City varchar(50) not null, -- City
    delivery_State varchar(2) not null, -- State abbreviation (e.g., CA)
    delivery_Zip varchar(10) not null, -- ZIP or postal code
    cc_number varchar(16) not null, -- Credit card number
    cc_expiration varchar(5) not null, -- Expiry date (MM/YY)
    cc_cvv varchar(3) not null, -- CVV code
    placed_at timestamp not null -- Timestamp of order placement
    );

-- 🌮 Create Taco table to store individual tacos within an order
create table if not exists Taco (
                                    id identity, -- Auto-generated primary key
                                    name varchar(50) not null, -- Taco name
    taco_order bigint not null, -- Foreign key to Taco_Order
    taco_order_key bigint not null, -- Position/index of taco in the order
    created_at timestamp not null -- Timestamp of taco creation
    );

-- 🧂 Create Ingredient table to store available ingredients
create table if not exists Ingredient (
                                          id varchar(10) not null primary key, -- Unique ingredient ID
    name varchar(25) not null, -- Ingredient name
    type varchar(10) not null -- Category (WRAP, PROTEIN, VEGGIES, etc.)
    );

-- 🔗 Create Ingredient_Ref table to link ingredients to tacos
create table if not exists Ingredient_Ref (
                                              ingredient varchar(10) not null, -- Foreign key to Ingredient
    taco bigint not null, -- Foreign key to Taco
    taco_key bigint not null -- Position/index of ingredient in taco
    );

-- 🔐 Add foreign key constraint to link Taco to Taco_Order
alter table Taco
    add foreign key (taco_order) references Taco_Order(id);

-- 🔐 Add foreign key constraint to link Ingredient_Ref to Ingredient
alter table Ingredient_Ref
    add foreign key (ingredient) references Ingredient(id);