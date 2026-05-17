CREATE TABLE tenants (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(255) NOT NULL,
    slug VARCHAR(255) UNIQUE NOT NULL,
    endereco TEXT,
    logo TEXT,
    cor_principal VARCHAR(9),
    cor_secundaria VARCHAR(9),
    data_criacao TIMESTAMP WITH TIME ZONE DEFAULT NOW()
    ativo BOOLEAN DEFAULT TRUE,
);

CREATE TABLE usuarios (
    id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL CHECK (role IN ('DONO', 'GERENTE', 'VENDEDOR')),
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE TABLE produtos (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    slug VARCHAR(255) UNIQUE NOT NULL,
    produto VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL DEFAULT 0,
    foto TEXT,
    categoria VARCHAR(55) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE TABLE pedidos (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    slug VARCHAR(255) UNIQUE NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
--     usuario_id UUID NOT NULL REFERENCES usuarios(id),
    cliente VARCHAR(255),
    mesa VARCHAR(50),
    status VARCHAR(50) DEFAULT 'PENDENTE',
    dataString VARCHAR(100),
    data_criacao TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE TABLE itens_pedido (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    pedido_id UUID NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,
    produto_id UUID NOT NULL REFERENCES produtos(id),
    quantidade INTEGER NOT NULL CHECK (quantidade > 0),
    preco_unitario DECIMAL(10, 2) NOT NULL
);