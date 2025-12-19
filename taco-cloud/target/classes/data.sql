-- 🔄 Clear existing ingredient references (junction table)
delete from Ingredient_Ref;

-- 🔄 Clear tacos (child table of Taco_Order)
delete from Taco;

-- 🔄 Clear taco orders (parent table)
delete from Taco_Order;

-- 🔄 Clear all existing ingredients
delete from Ingredient;

-- 🌮 Insert fresh ingredient data into the Ingredient table

-- Wraps
insert into Ingredient (id, name, type)
values ('FLTO', 'Flour Tortilla', 'WRAP'); -- Soft flour-based wrap
insert into Ingredient (id, name, type)
values ('COTO', 'Corn Tortilla', 'WRAP'); -- Gluten-free corn-based wrap

-- Proteins
insert into Ingredient (id, name, type)
values ('GRBF', 'Ground Beef', 'PROTEIN'); -- Classic taco meat
insert into Ingredient (id, name, type)
values ('CARN', 'Carnitas', 'PROTEIN'); -- Slow-cooked pork

-- Veggies
insert into Ingredient (id, name, type)
values ('TMTO', 'Diced Tomatoes', 'VEGGIES'); -- Fresh tomato chunks
insert into Ingredient (id, name, type)
values ('LETC', 'Lettuce', 'VEGGIES'); -- Crisp shredded lettuce

-- Cheeses
insert into Ingredient (id, name, type)
values ('CHED', 'Cheddar', 'CHEESE'); -- Sharp yellow cheese
insert into Ingredient (id, name, type)
values ('JACK', 'Monterrey Jack', 'CHEESE'); -- Mild white cheese

-- Sauces
insert into Ingredient (id, name, type)
values ('SLSA', 'Salsa', 'SAUCE'); -- Spicy tomato-based sauce
insert into Ingredient (id, name, type)
values ('SRCR', 'Sour Cream', 'SAUCE'); -- Cool creamy topping