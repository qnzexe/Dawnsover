-- ----------------------------------------------------------
-- Cleanup Login Server:
-- ----------------------------------------------------------
-- These queries are meant to delete any loginserver table you may have from previous.
-- More information: http://u3j-aion.united-extreme.com
-- ----------------------------------------------------------

DROP TABLE IF EXISTS account_data;
DROP TABLE IF EXISTS account_time;
DROP TABLE IF EXISTS aionshop_categories;
DROP TABLE IF EXISTS aionshop_items;
DROP TABLE IF EXISTS aionshop_transactions;
DROP TABLE IF EXISTS banned_ip;
DROP TABLE IF EXISTS gameservers;
