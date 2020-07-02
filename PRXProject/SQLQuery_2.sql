-- Drop the database 'PRXProject'
-- Connect to the 'master' database to run this snippet
USE master
GO
-- Uncomment the ALTER DATABASE statement below to set the database to SINGLE_USER mode if the drop database command fails because the database is in use.
-- ALTER DATABASE PRXProject SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
-- Drop the database if it exists
IF EXISTS (
    SELECT [name]
        FROM sys.databases
        WHERE [name] = N'PRXProject'
)
DROP DATABASE PRXProject
GO 


-- Create a new database called 'PRXProject'
-- Connect to the 'master' database to run this snippet
USE master
GO
-- Create the new database if it does not exist already
IF NOT EXISTS (
    SELECT [name]
        FROM sys.databases
        WHERE [name] = N'PRXProject'
)
CREATE DATABASE PRXProject
GO

USE PRXProject
GO

-- Create a new table called '[Brand]' in schema '[dbo]'
-- Drop the table if it already exists
IF OBJECT_ID('[dbo].[Brand]', 'U') IS NOT NULL
DROP TABLE [dbo].[Brand]
GO
-- Create the table in the specified schema
CREATE TABLE [dbo].[Brand]
(
    [Id] INT NOT NULL IDENTITY(1,1) PRIMARY KEY, -- Primary Key column
    [Name] NVARCHAR(50) NOT NULL
    -- Specify more columns here
);
GO

-- Create a new table called '[Laptop]' in schema '[dbo]'
-- Drop the table if it already exists
IF OBJECT_ID('[dbo].[Laptop]', 'U') IS NOT NULL
DROP TABLE [dbo].[Laptop]
GO
-- Create the table in the specified schema
CREATE TABLE [dbo].[Laptop]
(
    [Id] INT NOT NULL IDENTITY(1,1) PRIMARY KEY, -- Primary Key column
    [Name] NVARCHAR(500) NOT NULL,
    [Model] NVARCHAR(500) NOT NULL,
    [Price] INT NOT NULL,
    [CPU] NVARCHAR(500) NOT NULL,
    [VGA] NVARCHAR(500),
    [RAM] NVARCHAR(500),
    [HardDisk] NVARCHAR(500),
    [LCD] NVARCHAR(500),
    [Options] NVARCHAR(500),
    [Port] NVARCHAR(500),
    [OS] NVARCHAR(500),
    [Battery] NVARCHAR(500),
    [Weight] NVARCHAR(500),
    [Color] NVARCHAR(500),
    -- Specify more columns here
);
GO
-- Add a new column '[NewColumnName]' to table '[TableName]' in schema '[dbo]'
ALTER TABLE [dbo].[Laptop]
    ADD [BrandId] int NOT NULL
GO
ALTER TABLE Laptop ADD CONSTRAINT FK_Brand FOREIGN KEY (BrandId) references Brand(Id)  


SELECT COUNT(Model)
FROM Laptop

-- Delete rows from table '[Brand]' in schema '[dbo]'
DELETE FROM [dbo].[Brand]
GO

-- Delete rows from table '[Laptop]' in schema '[dbo]'
DELETE FROM [dbo].[Laptop]
GO