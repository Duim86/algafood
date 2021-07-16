alter table forma_de_pagamento add data_atualizacao datetime null;
update forma_de_pagamento set data_atualizacao = utc_timestamp;
alter table forma_de_pagamento modify data_atualizacao datetime not null;