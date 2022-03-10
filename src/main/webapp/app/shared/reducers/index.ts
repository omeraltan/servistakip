import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import servisIs from 'app/entities/servis-is/servis-is.reducer';
// prettier-ignore
import servisTemel from 'app/entities/servis-temel/servis-temel.reducer';
// prettier-ignore
import protokolTemel from 'app/entities/protokol-temel/protokol-temel.reducer';
// prettier-ignore
import irtibatTemel from 'app/entities/irtibat-temel/irtibat-temel.reducer';
// prettier-ignore
import metodTemel from 'app/entities/metod-temel/metod-temel.reducer';
// prettier-ignore
import projeTemel from 'app/entities/proje-temel/proje-temel.reducer';
// prettier-ignore
import servisProjeTemel from 'app/entities/servis-proje-temel/servis-proje-temel.reducer';
// prettier-ignore
import personel from 'app/entities/personel/personel.reducer';
// prettier-ignore
import kurumTemel from 'app/entities/kurum-temel/kurum-temel.reducer';
// prettier-ignore
import sorunTemel from 'app/entities/sorun-temel/sorun-temel.reducer';
// prettier-ignore
import tabloTemel from 'app/entities/tablo-temel/tablo-temel.reducer';
// prettier-ignore
import klasorTemel from 'app/entities/klasor-temel/klasor-temel.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  servisIs,
  servisTemel,
  protokolTemel,
  irtibatTemel,
  metodTemel,
  projeTemel,
  servisProjeTemel,
  personel,
  kurumTemel,
  sorunTemel,
  tabloTemel,
  klasorTemel,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
