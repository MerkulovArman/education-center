DROP TABLE IF EXISTS products;
CREATE TABLE products
(
  id         BIGINT not null GENERATED ALWAYS AS IDENTITY,
  name       VARCHAR(255) not null,
  price      DECIMAL(10,2) not null,
  count      BIGINT,
  created    TIMESTAMP not null DEFAULT CURRENT TIMESTAMP,
  PRIMARY KEY (id)
);