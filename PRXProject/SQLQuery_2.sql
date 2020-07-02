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
    [Name] NVARCHAR(50) NOT NULL,
    [Model] NVARCHAR(50) NOT NULL,
    [Price] DECIMAL NOT NULL,
    [CPU] NVARCHAR(50) NOT NULL,
    [VGA] NVARCHAR(50),
    [RAM] NVARCHAR(50),
    [HardDisk] NVARCHAR(50),
    [LCD] NVARCHAR(50),
    [Options] NVARCHAR(50),
    [Port] NVARCHAR(50),
    [Webcam] BIT NOT NULL,
    [FingerprintRecognition] BIT NOT NULL,
    [FaceRecognition] BIT NOT NULL,
    [OS] NVARCHAR(50),
    [Battery] NVARCHAR(50),
    [Size] NVARCHAR(50),
    [Weight] NVARCHAR(50),
    [Color] NVARCHAR(50),
    BrandId int,
    -- Specify more columns here
);
GO
-- Add a new column '[NewColumnName]' to table '[TableName]' in schema '[dbo]'
ALTER TABLE [dbo].[Laptop]
    ADD [BrandId] int NOT NULL
GO
ALTER TABLE Laptop ADD CONSTRAINT FK_Brand FOREIGN KEY (BrandId) references Brand(Id)  GO

-- Insert rows into table 'Brand' in schema '[dbo]'
INSERT INTO [dbo].[Brand] VALUES
('Lenovo'),('Apple'),('Asus')
-- Add more rows here
GO

-- Insert rows into table '' in schema '[dbo]'
INSERT INTO [dbo].[Laptop]
( -- Columns to insert data into
 [Model],[Name], [Price],[CPU],Webcam,FingerprintRecognition,FaceRecognition,BrandId
)
VALUES
( -- First row: values for the columns in the list above
 'Lenovo1','Lenovo Supervip1', 123126794,'i5',0,0,0,1
),
( -- First row: values for the columns in the list above
 'Lenovo2','Lenovo Supervip2', 123126794,'i5',0,0,0,1
),
( -- First row: values for the columns in the list above
 'Apple1','Apple Supervip1', 123126794,'i5',0,0,0,2
),
( -- First row: values for the columns in the list above
 'Asus1','Asus Supervip1', 123126794,'i5',0,0,0,3
)
GO

SELECT COUNT(Model)

-- Delete rows from table '[Brand]' in schema '[dbo]'
DELETE FROM [dbo].[Brand]
GO

-- Delete rows from table '[Laptop]' in schema '[dbo]'
DELETE FROM [dbo].[Laptop]
GO