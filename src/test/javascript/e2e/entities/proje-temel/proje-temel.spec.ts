import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ProjeTemelComponentsPage from './proje-temel.page-object';
import ProjeTemelUpdatePage from './proje-temel-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('ProjeTemel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let projeTemelComponentsPage: ProjeTemelComponentsPage;
  let projeTemelUpdatePage: ProjeTemelUpdatePage;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();
    await signInPage.username.sendKeys(username);
    await signInPage.password.sendKeys(password);
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    projeTemelComponentsPage = new ProjeTemelComponentsPage();
    projeTemelComponentsPage = await projeTemelComponentsPage.goToPage(navBarPage);
  });

  it('should load ProjeTemels', async () => {
    expect(await projeTemelComponentsPage.title.getText()).to.match(/Proje Temels/);
    expect(await projeTemelComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete ProjeTemels', async () => {
    const beforeRecordsCount = (await isVisible(projeTemelComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(projeTemelComponentsPage.table);
    projeTemelUpdatePage = await projeTemelComponentsPage.goToCreateProjeTemel();
    await projeTemelUpdatePage.enterData();
    expect(await isVisible(projeTemelUpdatePage.saveButton)).to.be.false;

    expect(await projeTemelComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(projeTemelComponentsPage.table);
    await waitUntilCount(projeTemelComponentsPage.records, beforeRecordsCount + 1);
    expect(await projeTemelComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await projeTemelComponentsPage.deleteProjeTemel();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(projeTemelComponentsPage.records, beforeRecordsCount);
      expect(await projeTemelComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(projeTemelComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
