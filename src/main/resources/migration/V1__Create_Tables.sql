-- 1. TABELA DE TENANTS (Atualizada sem CNPJ e com Endereço)
CREATE TABLE tenants (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(255) NOT NULL,
    slug VARCHAR(255) UNIQUE NOT NULL,
    endereco TEXT, -- Adicionado endereço
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 2. TABELA DE USUÁRIOS (Perfis de Acesso)
CREATE TABLE usuarios (
    id UUID PRIMARY KEY, -- Referencia auth.users(id) do Supabase
    tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL CHECK (role IN ('DONO', 'GERENTE', 'VENDEDOR')),
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 3. TABELA DE PRODUTOS (Refletindo sua IProduto)
CREATE TABLE produtos (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    produto VARCHAR(255) NOT NULL, -- Reflete 'produto' da sua interface
    preco DECIMAL(10, 2) NOT NULL DEFAULT 0,
    foto TEXT, -- Reflete 'foto' da sua interface
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 4. TABELA DE PEDIDOS (A "Capa" da sua IPedido)
CREATE TABLE pedidos (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    usuario_id UUID NOT NULL REFERENCES usuarios(id), -- Vendedor que lançou
    cliente VARCHAR(255), -- Reflete 'cliente' da sua interface
    mesa VARCHAR(50),     -- Reflete 'mesa' da sua interface
    total DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) DEFAULT 'pendente',
    dataString VARCHAR(100), -- Reflete o campo da sua interface
    data_criacao TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 5. ITENS DO PEDIDO (Onde o seu Array 'produtos' é armazenado no banco)
-- Um Pedido pode ter vários itens aqui. Cada item aponta para 1 produto.
CREATE TABLE itens_pedido (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    pedido_id UUID NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,
    produto_id UUID NOT NULL REFERENCES produtos(id),
    quantidade INTEGER NOT NULL CHECK (quantidade > 0),
    preco_unitario DECIMAL(10, 2) NOT NULL -- Salva o preço no momento da venda (histórico)
);